package com.example.monsterfestival.fragment_dir;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsterfestival.activity_dir.MainActivity;
import com.example.monsterfestival.classes_dir.Cart;
import com.example.monsterfestival.classes_dir.CartHelper;
import com.example.monsterfestival.classes_dir.Compare;
import com.example.monsterfestival.classes_dir.DataClass;
import com.example.monsterfestival.classes_dir.OnFragmentRemoveListener;
import com.example.monsterfestival.classes_dir.OnFragmentVisibleListener;
import com.example.monsterfestival.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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


public class DetailMonsterFragment extends Fragment implements OnFragmentRemoveListener {

    TextView detailID, detailDesc, detailName, detailAmbiente, detailCA, detailCAR, detailCOST, detailCategoria, detailDES, detailFOR, detailINT, detailPF, detailSAG, detailSfida, detailTaglia;
    FloatingActionButton addButton, exportButton;

    Dialog dialog;

    Fragment parent;
    OnFragmentVisibleListener fragmentVisibleListener;

    private String filePath;

    private final Lock ThreadLock = new ReentrantLock();

    private View rootView;

    public void setParent(Fragment _parent) {
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.recyclerview_progress_layout);
        dialog = builder.create();

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
                final Compare compare = Compare.getCompare();
                if (!compare.getFlag()) {
                    cart.add(dataClass, 1, getContext());
                }
                else {
                    compare.add(requireContext(), dataClass, compare.getNumero());
                }
                ((MainActivity) requireActivity()).tornaIndietro(2);
            }
        });

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
            else if (parent instanceof DetailPartyFragment) {
                addButton.setVisibility(View.INVISIBLE);
            }
        }

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
        boolean addButtonVisibility = addButton.getVisibility() == View.VISIBLE;
        if (addButtonVisibility)
            addButton.setVisibility(View.INVISIBLE);

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
            addButton.setVisibility(View.VISIBLE);

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