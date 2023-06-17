package com.example.monsterfestival;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfDocument;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;



import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class DetailFragment extends Fragment implements OnFragmentRemoveListener {

    TextView detailID, detailDesc, detailName, detailAmbiente, detailCA, detailCAR, detailCOST, detailCategoria, detailDES, detailFOR, detailINT, detailPF, detailSAG, detailSfida, detailTaglia;
    FloatingActionButton addButton, exportButton;

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
        rootView = inflater.inflate(R.layout.fragment_detail_monster, container, false);

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
        addButton = rootView.findViewById(R.id.add_btn);
        exportButton = rootView.findViewById(R.id.export_btn);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            detailID.setText(bundle.getString("ID"));
            detailDesc.setText(bundle.getString("Descrizione"));
            detailName.setText(bundle.getString("Nome"));
            detailAmbiente.setText(bundle.getString("Ambiente"));
            detailCA.setText(bundle.getString("CA"));
            detailCAR.setText(bundle.getString("CAR"));
            detailCOST.setText(bundle.getString("COST"));
            detailCategoria.setText(bundle.getString("Categoria"));
            detailDES.setText(bundle.getString("DES"));
            detailFOR.setText(bundle.getString("FOR"));
            detailINT.setText(bundle.getString("INT"));
            detailPF.setText(bundle.getString("PF"));
            detailSAG.setText(bundle.getString("SAG"));
            detailSfida.setText(bundle.getString("Sfida"));
            detailTaglia.setText(bundle.getString("Taglia"));
        }

        addButton.setOnClickListener(view1 -> {
            if (bundle != null) {
                Cart cart = CartHelper.getCart();
                ArrayList<String> dati = new ArrayList<>();
                dati.add(bundle.getString("ID"));
                dati.add(bundle.getString("Nome"));
                dati.add(bundle.getString("Descrizione"));
                dati.add(bundle.getString("Ambiente"));
                dati.add(bundle.getString("Categoria"));
                dati.add(bundle.getString("Taglia"));
                dati.add(bundle.getString("Sfida"));
                dati.add(bundle.getString("PF"));
                dati.add(bundle.getString("CA"));
                dati.add(bundle.getString("FOR"));
                dati.add(bundle.getString("DES"));
                dati.add(bundle.getString("COST"));
                dati.add(bundle.getString("INT"));
                dati.add(bundle.getString("SAG"));
                dati.add(bundle.getString("CAR"));
                DataClass dataClass = new DataClass(dati);
                cart.add(dataClass, 1, getContext());
            }
        });

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

        if (parent != null) {
            Fragment grandparentFragment = parent.getParentFragment();
            if (grandparentFragment != null) {
                    if (grandparentFragment instanceof HomeFragment) {
                        addButton.setVisibility(View.INVISIBLE);
                    }
                    else {
                        addButton.setVisibility(View.VISIBLE);
                    }
            }
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
            fragmentVisibleListener.onFragmentVisible(getParentFragmentManager(), this, getResources().getString(R.string.nome_detailt_monster));
        }
    }

    public void ripristinaVisibilitaElementi() {}

    private void createAndSharePdf(Context context) throws FileNotFoundException {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        Dialog dialog = builder.create();
        dialog.show();

        boolean addButtonVisibility = false;
        if (addButton.getVisibility() == View.VISIBLE)
        {
            addButtonVisibility = true;
            addButton.setVisibility(View.INVISIBLE);
        }
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

        /*// Crea un nuovo documento PDF
        PdfDocument document = new PdfDocument(new PdfWriter(filePath));
        Document pdfDocument = new Document(document);

        // Crea un oggetto Image da Bitmap e aggiungilo al documento PDF
        Image image = new Image(ImageDataFactory.create(bitmapData));
        pdfDocument.add(image);

        // Chiusura del documento
        document.close();
*/
        if (addButtonVisibility)
            addButton.setVisibility(View.VISIBLE);
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