package com.example.monsterfestival.fragment_dir;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsterfestival.R;
import com.example.monsterfestival.activity_dir.MainActivity;
import com.example.monsterfestival.adapter_dir.CommentAdapter;
import com.example.monsterfestival.classes_dir.Comment;
import com.example.monsterfestival.classes_dir.EventClass;
import com.example.monsterfestival.classes_dir.MonsterClass;
import com.example.monsterfestival.classes_dir.OnFragmentRemoveListener;
import com.example.monsterfestival.classes_dir.OnFragmentVisibleListener;
import com.example.monsterfestival.classes_dir.PartialVoteClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DetailPartyPostFragment extends Fragment implements OnFragmentRemoveListener {

    LinearLayout PartyPostLayout;
    TextView detailName, detailCA, detailCAR, detailCOST, detailDES, detailFOR, detailINT, detailPF, detailSAG, detailSfida,detailMostri,detailEvent;
    ImageButton authorButton, exportButton,commentButton,voteButton;
    RecyclerView Comments;
    boolean valutato;
    int oldCoerenza;
    int oldOriginalita;
    int oldBilanciamento;

    Bundle bundle ;
    Fragment parent;
    OnFragmentVisibleListener fragmentVisibleListener;
    String uidAutorePost;
    DetailPartyPostFragment myself=this;

    ArrayList<Comment> CommentsList = new ArrayList<>();
    ArrayList<EventClass>Eventi= new ArrayList<>();
    private String filePath;
    View rootView;
    private final Lock ThreadLock = new ReentrantLock();
    Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView= inflater.inflate(R.layout.fragment_detail_party_post, container, false);
        PartyPostLayout=rootView.findViewById(R.id.PartyPostsLayout);
        filePath = requireContext().getFilesDir() + File.separator + "MonsterFestival_SchedaMostro.pdf";
        bundle = this.getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.recyclerview_progress_layout);
        dialog = builder.create();

        detailName = rootView.findViewById(R.id.detailName);
        detailCA = rootView.findViewById(R.id.detailCA);
        detailCAR = rootView.findViewById(R.id.detailCAR);
        detailCOST = rootView.findViewById(R.id.detailCOST);
        detailDES = rootView.findViewById(R.id.detailDES);
        detailFOR = rootView.findViewById(R.id.detailFOR);
        detailINT = rootView.findViewById(R.id.detailINT);
        detailPF = rootView.findViewById(R.id.detailPF);
        detailSAG = rootView.findViewById(R.id.detailSAG);
        detailSfida = rootView.findViewById(R.id.detailSfida);

        detailMostri = rootView.findViewById(R.id.detailMostri);
        detailEvent = rootView.findViewById(R.id.detailEventTv);
        exportButton = rootView.findViewById(R.id.export_btn);
        //TODO onClickListeners
        authorButton = rootView.findViewById(R.id.autor_btn);
        commentButton =rootView.findViewById(R.id.comment_btn);
        voteButton=rootView.findViewById(R.id.vote_btn);
        Comments=rootView.findViewById(R.id.commentRw);

        //exportButton.setVisibility(View.INVISIBLE);
        if(bundle != null){
            Bundle b = this.getArguments();
            detailName.setText(b.getString("Nome"));
            detailCA.setText(b.getString("totCa"));
            detailCAR.setText(b.getString("totCar"));
            detailCOST.setText(b.getString("totCost"));
            detailDES.setText(b.getString("totDes"));
            detailFOR.setText(b.getString("totFor"));
            detailINT.setText(b.getString("totInt"));
            detailPF.setText(b.getString("totPf"));
            detailSAG.setText(b.getString("totSag"));
            detailSfida.setText(b.getString("totSfida"));
            uidAutorePost=b.getString("UidAutore");

            setTextClickable(detailMostri, bundle.getString("ListaMostri"), true);
            setTextClickable(detailEvent, bundle.getString("ListaEventi"),false);

            if(parent instanceof DetailAuthorFragment)
                authorButton.setVisibility(View.INVISIBLE);

            if(b.getString("ListaEventi")!=null && !b.getString("ListaEventi").isEmpty())
            {
                for(int i=0;i<b.getStringArrayList("nomiEventi").size();i++) {
                    String nome = b.getStringArrayList("nomiEventi").get(i);
                    String causa = b.getStringArrayList("causeEventi").get(i);
                    String reazione = b.getStringArrayList("reazioniEventi").get(i);
                    Eventi.add(new EventClass(nome, causa, reazione));
                }
            }

            ArrayList<String> UidAutoreCommentArray=bundle.getStringArrayList("UidAutoreCommentArray");
            ArrayList<String> TextCommentArray=bundle.getStringArrayList("TextCommentArray");
            ArrayList<String> CommentTimeArray=bundle.getStringArrayList("CommentTimeArray");

            Log.d("DetailMonsterPost","UidAutoreCommentArray: "+UidAutoreCommentArray);
            Log.d("DetailMonsterPost","TextCommentArray: "+TextCommentArray);
            Log.d("DetailMonsterPost","CommentTimeArray: "+CommentTimeArray);
            for (int i=0;i<CommentTimeArray.size();i++) {
                CommentsList.add(new Comment( UidAutoreCommentArray.get(i), TextCommentArray.get(i), CommentTimeArray.get(i)));
                Log.d("DetailMonsterPost","CommentList: "+CommentsList);
            }
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
            Comments.setLayoutManager(gridLayoutManager);
            CommentAdapter commentsAdapter = new CommentAdapter(CommentsList,this);
            Comments.setAdapter(commentsAdapter);


            String IdParty = bundle.getString("PostTime");
            String UidCurrentUser =  FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("User").child(UidCurrentUser).child("MyPartyVotes");
            DatabaseReference refUserInf = FirebaseDatabase.getInstance().getReference("UsersInformation").child(UidCurrentUser);
            getCurrentVote();

            authorButton.setOnClickListener(view -> {
                Bundle bun = new Bundle();
                bun.putString("UidAutore",uidAutorePost);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                DetailAuthorFragment RecyclerFragment = new DetailAuthorFragment();
                RecyclerFragment.setArguments(bun);
                nascondiElementi();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.flPartyPost, RecyclerFragment).addToBackStack(null).commit();
            });

            //TODO Debug
            exportButton.setOnClickListener(view1 -> {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    if (ThreadLock.tryLock()) {
                        try {
                            dialog.show();
                            try {
                                createAndSharePdf(requireContext());
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        } finally {
                            ThreadLock.unlock();
                        }
                    }
                }
                else {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    Toast.makeText(requireContext(), "Permesso Accesso ai File negato, impossibile esportare la Scheda Party", Toast.LENGTH_SHORT).show();
                }
            });

            commentButton.setOnClickListener(view -> {
                Log.d("DetailMonsterPost","commentButton Pressed");
                Dialog dialog = new Dialog(requireContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.popup_create_comment);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(true);
                dialog.show();

                dialog.findViewById(R.id.btnAnnulla).setOnClickListener(view1 -> dialog.dismiss());
                dialog.findViewById(R.id.btnSalva).setOnClickListener(view1 -> {
                    String timestamp = String.valueOf(System.currentTimeMillis());
                    String UidComment =  FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String comment = ((TextView) dialog.findViewById(R.id.editMulti_CommentText)).getText().toString();

                    Comment c = new Comment(UidComment,comment,timestamp);

                    DatabaseReference ref =  FirebaseDatabase.getInstance().getReference("PartyPosts").child(IdParty).child("commenti").child(timestamp);
                    ref.setValue(c).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "Commento Aggiunto", Toast.LENGTH_SHORT).show();
                            CommentsList.add(c);
                            GridLayoutManager newGridLayoutManager = new GridLayoutManager(getActivity(), 1);
                            Comments.setLayoutManager(newGridLayoutManager);
                            CommentAdapter newCommentsAdapter = new CommentAdapter(CommentsList,this);
                            Comments.setAdapter(newCommentsAdapter);
                            refUserInf.child("nCommenti").get().addOnCompleteListener(read -> {
                                if (read.isSuccessful()) {
                                    int old_nCommenti = read.getResult().getValue(int.class);
                                    refUserInf.child("nCommenti").setValue(old_nCommenti+1);
                                }
                            });
                        }
                        else {
                            Toast.makeText(requireContext(), "Errore", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.dismiss();
                });
            });

            voteButton.setOnClickListener(view -> {
                Log.d("DetailMonsterPost","commentButton Pressed");
                Dialog dialog = new Dialog(requireContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.popup_voto_mostro);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(true);
                if(valutato){
                    RatingBar Coerenza = dialog.findViewById(R.id.ratingBarCoerenza);
                    RatingBar Originalita = dialog.findViewById(R.id.ratingBarOriginalita);
                    RatingBar Bilanciamento = dialog.findViewById(R.id.ratingBarBilanciamento);
                    Coerenza.setRating((float) oldCoerenza /2);
                    Originalita.setRating((float) oldOriginalita /2);
                    Bilanciamento.setRating((float) oldBilanciamento /2);
                }
                dialog.show();


                dialog.findViewById(R.id.btnAnnulla).setOnClickListener(view1 -> dialog.dismiss());
                dialog.findViewById(R.id.btnSalva).setOnClickListener(view1 -> {
                    RatingBar Coerenza = dialog.findViewById(R.id.ratingBarCoerenza);
                    RatingBar Originalita = dialog.findViewById(R.id.ratingBarOriginalita);
                    RatingBar Bilanciamento = dialog.findViewById(R.id.ratingBarBilanciamento);

                    //controllo valori inseriti
                    Double coerenza = (double) (Coerenza.getRating()*2);
                    Log.d("DetailMonsterPost","CoerenzaInt: "+coerenza+" Rating: "+Coerenza.getRating()*2);
                    Double originalita = (double) (Originalita.getRating()*2);
                    Log.d("DetailMonsterPost","OriginalitaInt: "+originalita+" Rating: "+Originalita.getRating()*2);
                    Double bilanciamento = (double) (Bilanciamento.getRating()*2);
                    Log.d("DetailMonsterPost","BilanciamentoInt: "+bilanciamento+" Rating: "+Bilanciamento.getRating()*2);
                    if (coerenza == 0|| originalita == 0|| bilanciamento == 0) {
                        Toast.makeText(requireContext(), "Valutazione incompleta", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    DatabaseReference refInUser=refUser.child(IdParty);
                    if (valutato) {
                        DatabaseReference ref =  FirebaseDatabase.getInstance().getReference("PartyPosts").child(IdParty);
                        ref.get().addOnCompleteListener(read -> {
                            if (read.isSuccessful()) {
                                int nVoti = read.getResult().child("nVoti").getValue(int.class);
                                Double voteCoerenza = read.getResult().child("voteCoerenza").getValue(Double.class);
                                Double voteOriginalita = read.getResult().child("voteOriginalita").getValue(Double.class);
                                Double voteBilanciamento = read.getResult().child("voteBilanciamento").getValue(Double.class);

                                Double newVoteCoerenza = (voteCoerenza * nVoti + coerenza - oldCoerenza) / nVoti;
                                Double newVoteOriginalita = (voteOriginalita * nVoti + originalita - oldOriginalita) / nVoti;
                                Double newVoteBilanciamento = (voteBilanciamento * nVoti + bilanciamento - oldBilanciamento) / nVoti;

                                Double vote= (newVoteCoerenza+newVoteOriginalita+newVoteBilanciamento)/3;

                                PartialVoteClass myV = new PartialVoteClass(coerenza, originalita, bilanciamento);

                                refInUser.setValue(myV).addOnCompleteListener(writeUser -> {
                                    if (writeUser.isSuccessful()) {
                                        ref.child("voteCoerenza").setValue(newVoteCoerenza);
                                        ref.child("voteOriginalita").setValue(newVoteOriginalita);
                                        ref.child("voteBilanciamento").setValue(newVoteBilanciamento);
                                        ref.child("nVoti").setValue(nVoti);
                                        ref.child("vote").setValue(vote);
                                        getCurrentVote();
                                        Toast.makeText(requireContext(), "Valutazione Aggiornata", Toast.LENGTH_SHORT).show();
                                        //TODO aggiornamento pagina onUpdateListener
                                    }
                                    else {
                                        Toast.makeText(requireContext(), "Errore", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                Toast.makeText(requireContext(), "Errore", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        DatabaseReference ref =  FirebaseDatabase.getInstance().getReference("PartyPosts").child(IdParty);
                        ref.get().addOnCompleteListener(read -> {
                            if (read.isSuccessful()) {
                                int nVoti = read.getResult().child("nVoti").getValue(int.class);
                                Double voteCoerenza = read.getResult().child("voteCoerenza").getValue(Double.class);
                                Double voteOriginalita = read.getResult().child("voteOriginalita").getValue(Double.class);
                                Double voteBilanciamento = read.getResult().child("voteBilanciamento").getValue(Double.class);

                                Double newVoteCoerenza = (voteCoerenza * nVoti + coerenza) / (nVoti + 1);
                                Double newVoteOriginalita = (voteOriginalita * nVoti + originalita) / (nVoti + 1);
                                Double newVoteBilanciamento = (voteBilanciamento * nVoti + bilanciamento) / (nVoti + 1);
                                int newNVoti=nVoti+1;
                                Double newVote= (newVoteCoerenza+newVoteOriginalita+newVoteBilanciamento)/3;

                                PartialVoteClass myV = new PartialVoteClass(coerenza, originalita, bilanciamento);

                                refInUser.setValue(myV).addOnCompleteListener(writeUser -> {
                                    if (writeUser.isSuccessful()) {
                                        refUserInf.child("nVoti").get().addOnCompleteListener(read2 -> {
                                            if (read2.isSuccessful()) {
                                                int old_nVoti = read2.getResult().getValue(int.class);
                                                refUserInf.child("nVoti").setValue(old_nVoti+1);
                                                ref.child("voteCoerenza").setValue(newVoteCoerenza);
                                                ref.child("voteOriginalita").setValue(newVoteOriginalita);
                                                ref.child("voteBilanciamento").setValue(newVoteBilanciamento);
                                                ref.child("nVoti").setValue(newNVoti);
                                                ref.child("vote").setValue(newVote);
                                                getCurrentVote();
                                                Toast.makeText(requireContext(), "Valutazione Aggiunta", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        //TODO aggiornamento pagina onUpdateListener

                                    }
                                    else {
                                        Toast.makeText(requireContext(), "Errore", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                Toast.makeText(requireContext(), "Errore", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    dialog.dismiss();
                });
            });



        }





        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentVisibleListener) {
            fragmentVisibleListener = (OnFragmentVisibleListener) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (fragmentVisibleListener != null) {
            fragmentVisibleListener.onFragmentVisible(getParentFragmentManager(), this, getResources().getString(R.string.nome_detail_party));
        }
    }

    @Override
    public void ripristinaVisibilitaElementi() {
        PartyPostLayout.setVisibility(View.VISIBLE);
    }

    public void nascondiElementi() {
        PartyPostLayout.setVisibility(View.INVISIBLE);
    }

    public void setParent(Fragment parent) {
        this.parent = parent;
    }

    public void getCurrentVote()
    {
        String IdMonster = bundle.getString("PostTime");
        String UidVoto =  FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("User").child(UidVoto).child("MyPartyVotes");
        refUser.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().hasChild(IdMonster)) {
                    valutato = true;
                    oldCoerenza = task.getResult().child(IdMonster+"/voteCoerenza").getValue(int.class);
                    oldOriginalita = task.getResult().child(IdMonster+"/voteOriginalita").getValue(int.class);
                    oldBilanciamento = task.getResult().child(IdMonster+"/voteBilanciamento").getValue(int.class);
                }
                else
                    valutato=false;
            }
        });
    }

    private void createAndSharePdf(Context context) throws FileNotFoundException {
        int totalHeight = 0;
        int maxWidth = 0;
        int currentHeight = 0;

        exportButton.setVisibility(View.INVISIBLE);
        authorButton.setVisibility(View.INVISIBLE);
        ArrayList<Bitmap> sectionScreenshots = new ArrayList<>();

        sectionScreenshots.add(captureSectionScreenshot(rootView.findViewById(R.id.title_section)));
        sectionScreenshots.add(captureSectionScreenshot(rootView.findViewById(R.id.cardView_section)));
        sectionScreenshots.add(captureSectionScreenshot(rootView.findViewById(R.id.detailDesc)));
        sectionScreenshots.add(captureSectionScreenshot(rootView.findViewById(R.id.detailEvent)));

        // Calcola l'altezza totale e la larghezza massima tra gli screenshot delle sezioni
        for (Bitmap screenshot : sectionScreenshots) {
            totalHeight += screenshot.getHeight();
            maxWidth = Math.max(maxWidth, screenshot.getWidth());
        }

        // Crea una bitmap vuota con le dimensioni totali
        Bitmap fragmentBitmap = Bitmap.createBitmap(maxWidth, totalHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(fragmentBitmap);

        // Imposta lo sfondo della bitmap con lo stesso del fragment
        Rect sourceRect = new Rect(0, 0, rootView.getWidth(), rootView.getHeight());
        Rect destinationRect = new Rect(0, 0, maxWidth, totalHeight);
        Bitmap bitmapBackground = drawableToBitmap(getResources().getDrawable(R.drawable.background_image, null), rootView.getWidth(), rootView.getHeight());
        canvas.drawBitmap(bitmapBackground, sourceRect, destinationRect, new Paint());

        exportButton.setVisibility(View.VISIBLE);
        authorButton.setVisibility(View.VISIBLE);

        for (int i = 0; i < sectionScreenshots.size(); i++) {
            canvas.drawBitmap(sectionScreenshots.get(i), (float) ((rootView.getWidth() - sectionScreenshots.get(i).getWidth()) / 2), currentHeight, null);
            currentHeight += sectionScreenshots.get(i).getHeight();
        }

        // Converti il Bitmap in un array di byte
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        fragmentBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bitmapData = baos.toByteArray();

        // Crea un nuovo documento PDF
        PdfDocument document = new PdfDocument(new PdfWriter(filePath));
        document.setDefaultPageSize(new PageSize(maxWidth, totalHeight));
        Document pdfDocument = new Document(document);

        // Crea un oggetto Image da Bitmap e aggiungilo al documento PDF
        Image image = new Image(ImageDataFactory.create(bitmapData));
        pdfDocument.add(image);

        // Chiusura del documento
        document.close();

        dialog.dismiss();

        // Visualizzazione o condivisione del PDF
        File file = new File(filePath);
        if (file.exists()) {
            Uri pdfUri = FileProvider.getUriForFile(requireContext(), context.getApplicationContext().getPackageName() + ".fileprovider", file);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/pdf");
            shareIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.grantUriPermission(context.getApplicationContext().getPackageName(), pdfUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(shareIntent, "Salva o Condividi..."));
        }
    }

    private Bitmap captureSectionScreenshot(View sectionView) {
        Bitmap screenshot = Bitmap.createBitmap(sectionView.getWidth(), sectionView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(screenshot);
        sectionView.draw(canvas);
        return screenshot;
    }

    private Bitmap drawableToBitmap(Drawable drawable, int width, int height) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private void setTextClickable(TextView textView, String text, Boolean isMonster) {
        SpannableString spannableString = new SpannableString(text);

        // Definisci i pattern regex per estrarre i nomi dei mostri
        Pattern nomeMostroRegex = Pattern.compile("([^,(]+)(?= \\()");
        Pattern nomeEventoRegex = Pattern.compile("([^\n]+)(?=[\n.])");
        Matcher nomeMostroMatcher = nomeMostroRegex.matcher(text);
        Matcher nomeEventoMatcher = nomeEventoRegex.matcher(text);

        boolean firstName = false;

        if (isMonster)
        {
            // Itera sui match dei nomi dei mostri
            while (nomeMostroMatcher.find()) {
                // Estrai il nome dell'oggetto
                String nomeMostro = Objects.requireNonNull(nomeMostroMatcher.group(1)).trim();

                // Trova l'indice di inizio e fine del nome dell'oggetto nella stringa completa
                int startIndex = nomeMostroMatcher.start();
                if (firstName)
                    startIndex += 1;
                else
                    firstName = true;
                int endIndex = nomeMostroMatcher.end();

                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        // Esegui l'azione desiderata con l'oggetto corrente e il numero di occorrenze
                        Bundle b = new Bundle();

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Monster").child("ID");
                        ref.orderByChild("Nome").equalTo(nomeMostro).get().addOnCompleteListener(snapshot -> {

                            if (snapshot.isSuccessful()) {
                                if (snapshot.getResult().getChildrenCount() == 1) {
                                    for(DataSnapshot dataSnapshot : snapshot.getResult().getChildren()) {
                                        MonsterClass monster = new MonsterClass(dataSnapshot);

                                        b.putString("ID", monster.getID());
                                        b.putString("Ambiente", monster.getAmbiente());
                                        b.putString("CA", monster.getCa());
                                        b.putString("Categoria", monster.getCategoria());
                                        b.putString("Nome", monster.getNome());
                                        b.putString("PF", monster.getPf());
                                        b.putString("Sfida", String.valueOf(Float.parseFloat(monster.getSfida())));
                                        b.putString("Taglia", monster.getTaglia());
                                        b.putString("Descrizione", monster.getDescrizione());
                                        b.putString("CAR", monster.getCar());
                                        b.putString("COST", monster.getCost());
                                        b.putString("DES", monster.getDes());
                                        b.putString("FOR", monster.getFor());
                                        b.putString("INT", monster.getInt());
                                        b.putString("SAG", monster.getSag());

                                        AppCompatActivity activity = (AppCompatActivity) widget.getContext();
                                        DetailMonsterFragment RecyclerFragment = new DetailMonsterFragment();
                                        RecyclerFragment.setParent(myself);
                                        PartyPostLayout.setVisibility(View.INVISIBLE);
                                        RecyclerFragment.setArguments(b);
                                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.flPartyPost, RecyclerFragment).addToBackStack(null).commit();
                                    }
                                }
                            }
                            else {
                                Log.e("DetailMonsterPost", snapshot.getException().toString());
                                Log.e("DetailMonsterPost", snapshot.getException().getMessage());
                            }


                        });
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        // Personalizza lo stile del testo cliccabile (se desiderato)
                        ds.setUnderlineText(true); // Evidenzia il testo sottolineato
                        ds.setColor(ContextCompat.getColor(requireContext().getApplicationContext(), R.color.rossoPorpora)); // Imposta il colore del testo
                    }
                };

                // Applica il ClickableSpan al match nella SpannableString
                spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        else
        {
            int ievent = 0;
            Bundle thisBundle = this.getArguments();
            while (nomeEventoMatcher.find()) {
                // Estrai il nome dell'oggetto
                String nomeEvento = Objects.requireNonNull(nomeEventoMatcher.group(1)).trim();

                // Trova l'indice di inizio e fine del nome dell'oggetto nella stringa completa
                int startIndex = nomeEventoMatcher.start();
//                if (firstName)
//                    startIndex += 1;
//                else
//                    firstName = true;
                int endIndex = nomeEventoMatcher.end();

                final int temp = ievent;
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        // Esegui l'azione desiderata con l'oggetto corrente e il numero di occorrenze

                        EventClass event = Eventi.get(temp);

                        Log.d("DetailMonsterPost","commentButton Pressed");
                        Dialog dialog = new Dialog(requireContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.fragment_detail_event);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setCancelable(true);
                        TextView nome = dialog.findViewById(R.id.tvNomeEvento);
                        TextView causa = dialog.findViewById(R.id.tvCausaEvento);
                        TextView reazione = dialog.findViewById(R.id.tvReazioneEvento);

                        nome.setText(event.getNome());
                        causa.setText(event.getCausa());
                        reazione.setText(event.getReazione());

                        dialog.show();
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        // Personalizza lo stile del testo cliccabile (se desiderato)
                        ds.setUnderlineText(true); // Evidenzia il testo sottolineato
                        ds.setColor(ContextCompat.getColor(requireContext().getApplicationContext(), R.color.rossoPorpora)); // Imposta il colore del testo
                    }
                };

                // Applica il ClickableSpan al match nella SpannableString
                spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ievent++;
            }
        }

        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}