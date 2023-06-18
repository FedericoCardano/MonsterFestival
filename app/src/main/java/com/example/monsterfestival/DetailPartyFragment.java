package com.example.monsterfestival;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class DetailPartyFragment extends Fragment implements OnFragmentRemoveListener {

    TextView detailDiff, detailName, detailCA, detailCAR, detailCOST, detailDES, detailFOR, detailINT, detailPF, detailSAG, detailSfida;
    FloatingActionButton exportButton;

    Fragment parent;
    OnFragmentVisibleListener fragmentVisibleListener;

    private String filePath;

    private View rootView;

    void setParent(Fragment _parent) {
        parent = _parent;
    }

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail_party, container, false);

        filePath = requireContext().getFilesDir() + File.separator + "MonsterFestival_SchedaParty.pdf";

        detailName = rootView.findViewById(R.id.detailName);
        detailDiff = rootView.findViewById(R.id.detailDiff);
        detailCA = rootView.findViewById(R.id.detailCA);
        detailCAR = rootView.findViewById(R.id.detailCAR);
        detailCOST = rootView.findViewById(R.id.detailCOST);
        detailDES = rootView.findViewById(R.id.detailDES);
        detailFOR = rootView.findViewById(R.id.detailFOR);
        detailINT = rootView.findViewById(R.id.detailINT);
        detailPF = rootView.findViewById(R.id.detailPF);
        detailSAG = rootView.findViewById(R.id.detailSAG);
        detailSfida = rootView.findViewById(R.id.detailSfida);
        exportButton = rootView.findViewById(R.id.export_btn);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            detailName.setText(bundle.getString("Nome"));
            detailSfida.setText(bundle.getString("Sfida"));
            //detailDiff.setText(bundle.getString("Difficoltà"));
            detailCA.setText(bundle.getString("CA"));
            detailCAR.setText(bundle.getString("CAR"));
            detailCOST.setText(bundle.getString("COST"));
            detailDES.setText(bundle.getString("DES"));
            detailFOR.setText(bundle.getString("FOR"));
            detailINT.setText(bundle.getString("INT"));
            detailPF.setText(bundle.getString("PF"));
            detailSAG.setText(bundle.getString("SAG"));
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
        builder.setView(R.layout.progress_layout);
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

        canvas.drawBitmap(sectionScreenshots.get(0), 0, currentHeight, null);
        currentHeight += sectionScreenshots.get(0).getHeight();

        int offsetX = (sectionScreenshots.get(2).getWidth() - sectionScreenshots.get(1).getWidth()) / 2;
        canvas.drawBitmap(sectionScreenshots.get(1), offsetX, currentHeight, null);
        currentHeight += sectionScreenshots.get(1).getHeight();

        canvas.drawBitmap(sectionScreenshots.get(2), 0, currentHeight, null);

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
}