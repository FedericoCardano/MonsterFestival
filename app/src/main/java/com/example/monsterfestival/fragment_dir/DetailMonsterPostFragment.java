package com.example.monsterfestival.fragment_dir;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsterfestival.adapter_dir.CommentAdapter;
import com.example.monsterfestival.classes_dir.Comment;
import com.example.monsterfestival.classes_dir.OnFragmentRemoveListener;
import com.example.monsterfestival.classes_dir.OnFragmentVisibleListener;
import com.example.monsterfestival.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class DetailMonsterPostFragment extends Fragment implements OnFragmentRemoveListener {

    TextView detailID, detailDesc, detailName, detailAmbiente, detailCA, detailCAR, detailCOST, detailCategoria, detailDES, detailFOR, detailINT, detailPF, detailSAG, detailSfida, detailTaglia;
    FloatingActionButton autorButton, exportButton,commentButton,voteButton;
    RecyclerView Comments;

    Dialog dialog;

    Fragment parent;
    OnFragmentVisibleListener fragmentVisibleListener;

    private String filePath;

    private final Lock ThreadLock = new ReentrantLock();

    private View rootView;
    private ArrayList<Comment> CommentsList=new ArrayList<>();

    public void setParent(Fragment _parent) {
        parent = _parent;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail_monster_post, container, false);

        filePath = requireContext().getFilesDir() + File.separator + "MonsterFestival_SchedaMostro.pdf";

        detailID = rootView.findViewById(R.id.detailID);
        detailDesc = rootView.findViewById(R.id.detailDesc);
        detailName = rootView.findViewById(R.id.detailName);
        detailAmbiente = rootView.findViewById(R.id.detailAmbiente);
        detailCA = rootView.findViewById(R.id.detailCA);
        detailCAR = rootView.findViewById(R.id.detailCAR);
        detailCOST = rootView.findViewById(R.id.detailCOST);
        detailCategoria = rootView.findViewById(R.id.detailCategoria);
        detailDES = rootView.findViewById(R.id.detailDES);
        detailFOR = rootView.findViewById(R.id.detailFOR);
        detailINT = rootView.findViewById(R.id.detailINT);
        detailPF = rootView.findViewById(R.id.detailPF);
        detailSAG = rootView.findViewById(R.id.detailSAG);
        detailSfida = rootView.findViewById(R.id.detailSfida);
        detailTaglia = rootView.findViewById(R.id.detailTaglia);
        autorButton = rootView.findViewById(R.id.autor_btn);
        exportButton = rootView.findViewById(R.id.export_btn);
        commentButton =rootView.findViewById(R.id.comment_btn);
        voteButton=rootView.findViewById(R.id.vote_btn);
        Comments=rootView.findViewById(R.id.commentRw);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.recyclerview_progress_layout);
        dialog = builder.create();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ArrayList<String> mon = bundle.getStringArrayList("monster");
            detailID.setText(mon.get(0));
            detailName.setText(mon.get(1));
            detailDesc.setText(mon.get(2));
            detailAmbiente.setText(mon.get(3));
            detailCategoria.setText(mon.get(4));
            detailTaglia.setText(mon.get(5));
            Log.d("DetailMonsterPost","TagliaBundle: "+mon.get(5));
            detailSfida.setText(mon.get(6));
//                    float sfida = Float.parseFloat(bundle.getString("Sfida"));
//                    if (((int) sfida) != sfida)
//                        detailSfida.setText(String.valueOf(sfida));
//                    else
//                        detailSfida.setText(String.valueOf((int) sfida));
            detailPF.setText(mon.get(7));
            detailCA.setText(mon.get(8));
            detailFOR.setText(mon.get(9));
            detailDES.setText(mon.get(10));
            detailCOST.setText(mon.get(11));
            detailINT.setText(mon.get(12));
            detailSAG.setText(mon.get(13));
            detailCAR.setText(mon.get(14));

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
        }
        //TODO autorButtonListener
       // autorButton.setOnClickListener(view1 -> {
//            if (bundle != null) {
//                Cart cart = CartHelper.getCart();
//                ArrayList<String> dati = new ArrayList<>();
//                dati.add(bundle.getString("ID"));
//                dati.add(bundle.getString("Nome"));
//                dati.add(bundle.getString("Descrizione"));
//                dati.add(bundle.getString("Ambiente"));
//                dati.add(bundle.getString("Categoria"));
//                dati.add(bundle.getString("Taglia"));
//                dati.add(bundle.getString("Sfida"));
//                dati.add(bundle.getString("PF"));
//                dati.add(bundle.getString("CA"));
//                dati.add(bundle.getString("FOR"));
//                dati.add(bundle.getString("DES"));
//                dati.add(bundle.getString("COST"));
//                dati.add(bundle.getString("INT"));
//                dati.add(bundle.getString("SAG"));
//                dati.add(bundle.getString("CAR"));
//                MonsterClass monsterClass = new MonsterClass(dati);
//                final Compare compare = Compare.getCompare();
//                if (!compare.getFlag()) {
//                    cart.add(monsterClass, 1, getContext());
//                }
//                else {
//                    compare.add(requireContext(), monsterClass, compare.getNumero());
//                }
//                ((MainActivity) requireActivity()).tornaIndietro(2);
//            }
//      });


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
                String IdMonster = bundle.getStringArrayList("monster").get(0);

                Comment c = new Comment(UidComment,comment,timestamp);

                DatabaseReference ref =  FirebaseDatabase.getInstance().getReference("Posts").child(IdMonster).child("commenti").child(timestamp);
                ref.setValue(c).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(requireContext(), "Commento Aggiunto", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(requireContext(), "Errore", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            });
        });

        //TODO VoteListener
        voteButton.setOnClickListener(view -> {
            Log.d("DetailMonsterPost","commentButton Pressed");
            Dialog dialog = new Dialog(requireContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.popup_voto_mostro);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(true);
            dialog.show();

            dialog.findViewById(R.id.btnAnnulla).setOnClickListener(view1 -> dialog.dismiss());
            dialog.findViewById(R.id.btnSalva).setOnClickListener(view1 -> {
                RatingBar Coerenza = dialog.findViewById(R.id.ratingBarCoerenza);
                RatingBar Originalita = dialog.findViewById(R.id.ratingBarOriginalita);
                RatingBar Bilanciamento = dialog.findViewById(R.id.ratingBarBilanciamento);

                int coerenza = (int) (Coerenza.getRating()*2);
                Log.d("DetailMonsterPost","CoerenzaInt: "+coerenza+" Rating: "+Coerenza.getRating()*2);
                int originalita = (int) (Originalita.getRating()*2);
                Log.d("DetailMonsterPost","OriginalitaInt: "+originalita+" Rating: "+Originalita.getRating()*2);
                int bilanciamento = (int) (Bilanciamento.getRating()*2);
                Log.d("DetailMonsterPost","BilanciamentoInt: "+bilanciamento+" Rating: "+Bilanciamento.getRating()*2);

                String IdMonster = bundle.getStringArrayList("monster").get(0);
                String timestamp = String.valueOf(System.currentTimeMillis());
                String UidComment =  FirebaseAuth.getInstance().getCurrentUser().getUid();

//                Comment c = new Comment(UidComment,comment,timestamp);
//
//                DatabaseReference ref =  FirebaseDatabase.getInstance().getReference("Posts").child(IdMonster).child("commenti").child(timestamp);
//                ref.setValue(c).addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(requireContext(), "Commento Aggiunto", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        Toast.makeText(requireContext(), "Errore", Toast.LENGTH_SHORT).show();
//                    }
//                });
                dialog.dismiss();
            });
        });

        //TODO aggiornamento pagina onUpdateListener
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
                Toast.makeText(requireContext(), "Permesso Accesso ai File negato, impossibile esportare la Scheda Mostro", Toast.LENGTH_SHORT).show();
            }
        });

        rootView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
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
            fragmentVisibleListener.onFragmentVisible(getParentFragmentManager(), this, getResources().getString(R.string.nome_detail_monster));
        }
    }

    public void ripristinaVisibilitaElementi() {}

    private void createAndSharePdf(Context context) throws FileNotFoundException {
        int totalHeight = 0;
        int maxWidth = 0;
        int currentHeight = 0;

        exportButton.setVisibility(View.INVISIBLE);
        boolean addButtonVisibility = autorButton.getVisibility() == View.VISIBLE;
        if (addButtonVisibility)
            autorButton.setVisibility(View.INVISIBLE);

        ArrayList<Bitmap> sectionScreenshots = new ArrayList<>();

        sectionScreenshots.add(captureSectionScreenshot(rootView.findViewById(R.id.title_section)));
        sectionScreenshots.add(captureSectionScreenshot(rootView.findViewById(R.id.cardView_section)));
        sectionScreenshots.add(captureSectionScreenshot(rootView.findViewById(R.id.detailDesc)));

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
        Bitmap bitmapBackground = drawableToBitmap(rootView.getBackground(), rootView.getWidth(), rootView.getHeight());
        canvas.drawBitmap(bitmapBackground, sourceRect, destinationRect, new Paint());

        exportButton.setVisibility(View.VISIBLE);
        if (addButtonVisibility)
            autorButton.setVisibility(View.VISIBLE);

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
}