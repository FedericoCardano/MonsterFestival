package com.example.monsterfestival.fragment_dir;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.customsearchlibrary.NativeLib;
import com.example.monsterfestival.classes_dir.DataClass;
import com.example.monsterfestival.classes_dir.OnFragmentRemoveListener;
import com.example.monsterfestival.classes_dir.OnFragmentVisibleListener;
import com.example.monsterfestival.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DetailPartyFragment extends Fragment implements OnFragmentRemoveListener {

    TextView detailDiff, detailName, detailCA, detailCAR, detailCOST, detailDES, detailFOR, detailINT, detailPF, detailSAG, detailSfida, detailMostri;
    FloatingActionButton exportButton;

    Fragment parent;
    OnFragmentVisibleListener fragmentVisibleListener;

    private String filePath;

    private View rootView;

    public void setParent(Fragment _parent) {
        parent = _parent;
    }

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail_party, container, false);

        filePath = requireContext().getFilesDir() + File.separator + "MonsterFestival_SchedaParty.pdf";

        detailName = rootView.findViewById(R.id.detailName);
        //detailDiff = rootView.findViewById(R.id.detailDiff);
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

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            detailName.setText(bundle.getString("NomeParty"));
            detailSfida.setText(bundle.getString("totSfida"));
            //detailDiff.setText(bundle.getString("Difficoltà"));
            detailCA.setText(bundle.getString("totCA"));
            detailCAR.setText(bundle.getString("totCAR"));
            detailCOST.setText(bundle.getString("totCOST"));
            detailDES.setText(bundle.getString("totDES"));
            detailFOR.setText(bundle.getString("totFOR"));
            detailINT.setText(bundle.getString("totINT"));
            detailPF.setText(bundle.getString("totPF"));
            detailSAG.setText(bundle.getString("totSAG"));
            setTextClickable(detailMostri, bundle.getString("Mostri"));
        }

        exportButton.setOnClickListener(view1 -> {
            if (Build.VERSION.SDK_INT > 29 || ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                try {
                    createAndSharePdf(requireContext());
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            else
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.recyclerview_progress_layout);
        Dialog dialog = builder.create();
        dialog.show();

        exportButton.setVisibility(View.INVISIBLE);

        int totalHeight = 0;
        int maxWidth = 0;
        int currentHeight = 0;

        ArrayList<Bitmap> sectionScreenshots = new ArrayList<>();

        sectionScreenshots.add(captureSectionScreenshot(rootView.findViewById(R.id.title_section)));
        sectionScreenshots.add(captureSectionScreenshot(rootView.findViewById(R.id.cardView_section)));
        sectionScreenshots.add(captureSectionScreenshot(rootView.findViewById(R.id.detailDesc)));

        // Calcola l'altezza totale e la larghezza massima tra gli screenshot delle sezioni
        for (Bitmap screenshot : sectionScreenshots) {
            totalHeight += screenshot.getHeight();
            maxWidth = Math.max(maxWidth, screenshot.getWidth());
        }

        dialog.dismiss();

        // Crea una bitmap vuota con le dimensioni totali
        Bitmap fragmentBitmap = Bitmap.createBitmap(maxWidth, totalHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(fragmentBitmap);

        // Imposta lo sfondo della bitmap con lo stesso del fragment
        rootView.getBackground().draw(canvas);

        for (int i = 0; i < sectionScreenshots.size(); i++) {
            canvas.drawBitmap(sectionScreenshots.get(i), (Integer) ((rootView.getWidth() - sectionScreenshots.get(i).getWidth()) / 2), currentHeight, null);
            currentHeight += sectionScreenshots.get(i).getHeight();
        }

        dialog.show();

        // Converti il Bitmap in un array di byte
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        fragmentBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bitmapData = baos.toByteArray();

        // Crea un nuovo documento PDF
        PdfDocument document = new PdfDocument(new PdfWriter(filePath));
        Document pdfDocument = new Document(document);

        // Crea un oggetto Image da Bitmap e aggiungilo al documento PDF
        Image image = new Image(ImageDataFactory.create(bitmapData));
        pdfDocument.add(image);

        // Chiusura del documento
        document.close();

        exportButton.setVisibility(View.VISIBLE);

        // Visualizzazione o condivisione del PDF
        File file = new File(filePath);
        if (file.exists()) {
            Uri pdfUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/pdf");
            shareIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(shareIntent, "Salva o Condividi..."));
        }

        dialog.dismiss();
    }

    private Bitmap captureSectionScreenshot(View sectionView) {
        Bitmap screenshot = Bitmap.createBitmap(sectionView.getWidth(), sectionView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(screenshot);
        sectionView.draw(canvas);
        return screenshot;
    }

    private void setTextClickable(TextView textView, String text) {
        SpannableString spannableString = new SpannableString(text);

        // Definisci i pattern regex per estrarre i nomi dei mostri
        Pattern nomeMostroRegex = Pattern.compile("([^, (]+)(?= \\()");
        Matcher nomeMostroMatcher = nomeMostroRegex.matcher(text);

        // Itera sui match dei nomi dei mostri
        while (nomeMostroMatcher.find()) {
            // Estrai il nome dell'oggetto
            String nomeMostro = Objects.requireNonNull(nomeMostroMatcher.group(1)).trim();

            // Trova l'indice di inizio e fine del nome dell'oggetto nella stringa completa
            int startIndex = nomeMostroMatcher.start();
            int endIndex = nomeMostroMatcher.end();

            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    // Esegui l'azione desiderata con l'oggetto corrente e il numero di occorrenze
                    Bundle b = new Bundle();

                    SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                    NativeLib objectNativeLib = new NativeLib(new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class));

                    DataClass monster = new DataClass(objectNativeLib.getMostro(nomeMostro));

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
}