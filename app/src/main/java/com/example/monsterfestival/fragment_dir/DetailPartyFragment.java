package com.example.monsterfestival.fragment_dir;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.customsearchlibrary.NativeLib;
import com.example.monsterfestival.classes_dir.MonsterClass;
import com.example.monsterfestival.classes_dir.OnFragmentRemoveListener;
import com.example.monsterfestival.classes_dir.OnFragmentVisibleListener;
import com.example.monsterfestival.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
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


public class DetailPartyFragment extends Fragment implements OnFragmentRemoveListener {

    TextView detailDiff, detailName, detailCA, detailCAR, detailCOST, detailDES, detailFOR, detailINT, detailPF, detailSAG, detailSfida, detailMostri;
    FloatingActionButton exportButton;

    Dialog dialog;

    Fragment parent;
    OnFragmentVisibleListener fragmentVisibleListener;

    ProgressBar progressBar;
    private String filePath;

    private final Lock ThreadLock = new ReentrantLock();

    private View rootView;

    public void setParent(Fragment _parent) {
        parent = _parent;
    }

    
    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail_party, container, false);

        filePath = requireContext().getFilesDir() + File.separator + "MonsterFestival_SchedaParty.pdf";

        detailName = rootView.findViewById(R.id.detailName);
        detailCA = rootView.findViewById(R.id.detailCA);
        detailCAR = rootView.findViewById(R.id.detailCAR);
        detailCOST = rootView.findViewById(R.id.detailCOST);
        detailDES = rootView.findViewById(R.id.detailDES);
        detailFOR = rootView.findViewById(R.id.detailFOR);
        detailINT = rootView.findViewById(R.id.detailINT);
        detailPF = rootView.findViewById(R.id.detailPF);
        detailSAG = rootView.findViewById(R.id.detailSAG);
        detailMostri = rootView.findViewById(R.id.detailMostri);
        detailSfida = rootView.findViewById(R.id.detailSfida);
        exportButton = rootView.findViewById(R.id.export_btn);

        detailDiff = rootView.findViewById(R.id.detailDifficulty);
        progressBar = rootView.findViewById(R.id.DifficultyProgressBar);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.recyclerview_progress_layout);
        dialog = builder.create();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            detailName.setText(bundle.getString("NomeParty"));
            float sfida = Float.parseFloat(bundle.getString("totSfida"));
            if (((int) sfida) != sfida)
                detailSfida.setText(String.valueOf(sfida));
            else
                detailSfida.setText(String.valueOf((int) sfida));
            detailCA.setText(bundle.getString("totCA"));
            detailCAR.setText(bundle.getString("totCAR"));
            detailCOST.setText(bundle.getString("totCOST"));
            detailDES.setText(bundle.getString("totDES"));
            detailFOR.setText(bundle.getString("totFOR"));
            detailINT.setText(bundle.getString("totINT"));
            detailPF.setText(bundle.getString("totPF"));
            detailSAG.setText(bundle.getString("totSAG"));
            setTextClickable(detailMostri, bundle.getString("Mostri"));

            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            int nMember = sharedPreferences.getInt("NumAvventurieri", 1);
            int lvMember = sharedPreferences.getInt("LvAvventurieri", 1);

            switch (difficulty(nMember,lvMember,bundle.getString("NomeParty"))){
                case 0:{//facile
                    detailDiff.setText(getResources().getString(R.string.facile));
                    progressBar.setProgress(25);
                    progressBar.setProgressTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.verde, null)));
                    break;
                }
                case 1:{//medio
                    detailDiff.setText(getResources().getString(R.string.medio));
                    progressBar.setProgress(50);
                    progressBar.setProgressTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.giallo, null)));
                    break;
                }
                case 2:{//difficile
                    detailDiff.setText(getResources().getString(R.string.difficile));
                    progressBar.setProgress(75);
                    progressBar.setProgressTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.rosso, null)));
                    break;
                }
                case 3:{//mortale
                    detailDiff.setText(getResources().getString(R.string.mortale));
                    progressBar.setProgress(100);
                    progressBar.setProgressTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.purple_dif, null)));
                    break;
                }
                default:{
                    //TODO messaggio di errore
                    break;
                }
            }
        }

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
            fragmentVisibleListener.onFragmentVisible(getParentFragmentManager(), this, getResources().getString(R.string.nome_detail_party));
        }
    }

    public void ripristinaVisibilitaElementi() {}

    private void createAndSharePdf(Context context) throws FileNotFoundException {
        int totalHeight = 0;
        int maxWidth = 0;
        int currentHeight = 0;

        exportButton.setVisibility(View.INVISIBLE);

        ArrayList<Bitmap> sectionScreenshots = new ArrayList<>();

        sectionScreenshots.add(captureSectionScreenshot(rootView.findViewById(R.id.title_section)));
        sectionScreenshots.add(captureSectionScreenshot(rootView.findViewById(R.id.cardView_section)));
        sectionScreenshots.add(captureSectionScreenshot(rootView.findViewById(R.id.difficultyLayout)));
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

    private void setTextClickable(TextView textView, String text) {
        SpannableString spannableString = new SpannableString(text);

        // Definisci i pattern regex per estrarre i nomi dei mostri
        Pattern nomeMostroRegex = Pattern.compile("([^,(]+)(?= \\()");
        Matcher nomeMostroMatcher = nomeMostroRegex.matcher(text);

        boolean firstName = false;

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

                    SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                    NativeLib objectNativeLib = new NativeLib(new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class));

                    MonsterClass monster = new MonsterClass(objectNativeLib.getMostro(nomeMostro));

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
                    RecyclerFragment.setParent(DetailPartyFragment.this);
                    RecyclerFragment.setArguments(b);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_access_monster, RecyclerFragment ).addToBackStack(null).commit();
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

        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    int sfida2exp(double sfida) {
        if (sfida == 0)
            return 10;
        else if (sfida >= 0.125 - 0.0001 && sfida <= 1)
            return 25 * (int) sfida * 8;
        else {
            switch ((int) sfida) {
                case 2:
                    return 450;
                case 3:
                    return 700;
                case 4:
                    return 1100;
                case 5:
                    return 1800;
                case 6:
                    return 2300;
                case 7:
                    return 2900;
                case 8:
                    return 3900;
                case 9:
                    return 5000;
                case 10:
                    return 5900;
                case 11:
                    return 7200;
                case 12:
                    return 8400;
                case 13:
                    return 10000;
                case 14:
                    return 11500;
                case 15:
                    return 13000;
                case 16:
                    return 15000;
                case 17:
                    return 18000;
                case 18:
                    return 20000;
                case 19:
                    return 22000;
                case 20:
                    return 25000;
                case 21:
                    return 33000;
                case 22:
                    return 41000;
                case 23:
                    return 50000;
                case 24:
                    return 62000;
                case 25:
                    return 75000;
                case 26:
                    return 90000;
                case 27:
                    return 105000;
                case 28:
                    return 120000;
                case 29:
                    return 135000;
                case 30:
                    return 155000;
                default:
                    //TODO messaggio di errore
                    return 0;
            }
        }


    }

    int difficultyToHead(int lvMember, int EXP) {
        if (EXP < 1)
            return 0;
        switch (lvMember) {
            case 1:
                if (EXP < 50)
                    return 0;
                else if (EXP < 75)
                    return 1;
                else if (EXP < 100)
                    return 2;
                else
                    return 3;

            case 2:
                if (EXP < 100)
                    return 0;
                else if (EXP < 150)
                    return 1;
                else if (EXP < 200)
                    return 2;
                else
                    return 3;

            case 3:
                if (EXP < 150)
                    return 0;
                else if (EXP < 225)
                    return 1;
                else if (EXP < 400)
                    return 2;
                else
                    return 3;

            case 4:
                if (EXP < 250)
                    return 0;
                else if (EXP < 375)
                    return 1;
                else if (EXP < 500)
                    return 2;
                else
                    return 3;

            case 5:
                if (EXP < 500)
                    return 0;
                else if (EXP < 750)
                    return 1;
                else if (EXP < 1100)
                    return 2;
                else
                    return 3;

            case 6:
                if (EXP < 600)
                    return 0;
                else if (EXP < 900)
                    return 1;
                else if (EXP < 1400)
                    return 2;
                else
                    return 3;

            case 7:
                if (EXP < 750)
                    return 0;
                else if (EXP < 1100)
                    return 1;
                else if (EXP < 1700)
                    return 2;
                else
                    return 3;

            case 8:
                if (EXP < 900)
                    return 0;
                else if (EXP < 1400)
                    return 1;
                else if (EXP < 2100)
                    return 2;
                else
                    return 3;

            case 9:
                if (EXP < 1100)
                    return 0;
                else if (EXP < 1600)
                    return 1;
                else if (EXP < 2400)
                    return 2;
                else
                    return 3;

            case 10:
                if (EXP < 1200)
                    return 0;
                else if (EXP < 1900)
                    return 1;
                else if (EXP < 2800)
                    return 2;
                else
                    return 3;

            case 11:
                if (EXP < 1600)
                    return 0;
                else if (EXP < 2400)
                    return 1;
                else if (EXP < 3600)
                    return 2;
                else
                    return 3;

            case 12:
                if (EXP < 2000)
                    return 0;
                else if (EXP < 3000)
                    return 1;
                else if (EXP < 4500)
                    return 2;
                else
                    return 3;

            case 13:
                if (EXP < 2200)
                    return 0;
                else if (EXP < 3400)
                    return 1;
                else if (EXP < 5100)
                    return 2;
                else
                    return 3;

            case 14:
                if (EXP < 2500)
                    return 0;
                else if (EXP < 3800)
                    return 1;
                else if (EXP < 5700)
                    return 2;
                else
                    return 3;

            case 15:
                if (EXP < 2800)
                    return 0;
                else if (EXP < 4300)
                    return 1;
                else if (EXP < 6400)
                    return 2;
                else
                    return 3;

            case 16:
                if (EXP < 3200)
                    return 0;
                else if (EXP < 4800)
                    return 1;
                else if (EXP < 7200)
                    return 2;
                else
                    return 3;

            case 17:
                if (EXP < 3900)
                    return 0;
                else if (EXP < 5900)
                    return 1;
                else if (EXP < 8800)
                    return 2;
                else
                    return 3;

            case 18:
                if (EXP < 4200)
                    return 0;
                else if (EXP < 6300)
                    return 1;
                else if (EXP < 9500)
                    return 2;
                else
                    return 3;

            case 19:
                if (EXP < 4900)
                    return 0;
                else if (EXP < 7300)
                    return 1;
                else if (EXP < 10900)
                    return 2;
                else
                    return 3;

            case 20:
                if (EXP < 5700)
                    return 0;
                else if (EXP < 8500)
                    return 1;
                else if (EXP < 12700)
                    return 2;
                else
                    return 3;

            default:
                //TODO messaggio di errore
                return 0;
        }
    }

    int difficulty(int nMember, int lvMember, String nomeParty ) {
        int Lv;
        int expAmount = 0;
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        NativeLib objectNativeLib = new NativeLib(new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class));
        ArrayList<ArrayList<String>> Party = objectNativeLib.getPartyWithName(nomeParty);

        //Gestione input dell'utente
        if (nMember < 1)
            nMember = 1;
        if (lvMember < 1)
            Lv = 1;
        else Lv = Math.min(lvMember, 20);


        for (int i = 0; i < Party.size(); i++)
            expAmount += Integer.parseInt(Party.get(i).get(0)) * sfida2exp(Double.parseDouble(Party.get(i).get(7)));
        int expToHaad = expAmount / nMember + 1;
        return difficultyToHead(Lv, expToHaad);


    }
}