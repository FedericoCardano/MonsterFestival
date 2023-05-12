package com.example.monsterfestival;

//import static com.example.monsterfestival.SearchMonstersActivity.clearClicked;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchFiltersActivity extends AppCompatActivity implements View.OnClickListener {

    TextView clearFilters;
    LinearLayout ambieteArtico, ambieteCatacombe, ambieteCaverna, ambieteCittatina, ambieteCollina, ambieteCosta, ambieteDeserto, ambieteDungeon, ambieteErrante, ambieteForesta, ambieteGiungla, ambieteInferno, ambieteLago, ambieteLimbo, ambieteMontagna, ambieteNomade, ambieteOvunque, ambietePalude, ambieteParadiso, ambietePianoDelFuoco, ambietePianoDellAcqua, ambietePianoDellAria, ambietePianoDellaTerra, ambietePianura, ambieteProfonditàMarine, ambieteRovine, ambieteServitore, ambieteTerreSelvaggie, ambieteUnderdark, ambieteVulcano;
    LinearLayout categoriaAberrazione, categoriaBestia, categoriaCelestiale, categoriaCostrutto, categoriaDrago, categoriaElementale, categoriaFolletto, categoriaGigante, categoriaImmondo, categoriaMelma, categoriaMostruosità, categoriaNonMorto, categoriaUmanoide, categoriaVegetale;
    LinearLayout tagliaEnorme, tagliaGrande, tagliaMastodontica, tagliaMedia, tagliaMinuscola, tagliaPiccola;


    private int white, rossoPorpora, cream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filters);

        initColors();

        initAll();

        clearFilters.setOnClickListener(this);

        ambieteArtico.setOnClickListener(this);
        ambieteCatacombe.setOnClickListener(this);
        ambieteCaverna.setOnClickListener(this);
        ambieteCittatina.setOnClickListener(this);
        ambieteCollina.setOnClickListener(this);
        ambieteCosta.setOnClickListener(this);
        ambieteDeserto.setOnClickListener(this);
        ambieteDungeon.setOnClickListener(this);
        ambieteErrante.setOnClickListener(this);
        ambieteForesta.setOnClickListener(this);
        ambieteGiungla.setOnClickListener(this);
        ambieteInferno.setOnClickListener(this);
        ambieteLago.setOnClickListener(this);
        ambieteLimbo.setOnClickListener(this);
        ambieteMontagna.setOnClickListener(this);
        ambieteNomade.setOnClickListener(this);
        ambieteOvunque.setOnClickListener(this);
        ambietePalude.setOnClickListener(this);
        ambieteParadiso.setOnClickListener(this);
        ambietePianoDelFuoco.setOnClickListener(this);
        ambietePianoDellAcqua.setOnClickListener(this);
        ambietePianoDellAria.setOnClickListener(this);
        ambietePianoDellaTerra.setOnClickListener(this);
        ambietePianura.setOnClickListener(this);
        ambieteProfonditàMarine.setOnClickListener(this);
        ambieteRovine.setOnClickListener(this);
        ambieteServitore.setOnClickListener(this);
        ambieteTerreSelvaggie.setOnClickListener(this);
        ambieteUnderdark.setOnClickListener(this);
        ambieteVulcano.setOnClickListener(this);

        categoriaAberrazione.setOnClickListener(this);
        categoriaBestia.setOnClickListener(this);
        categoriaCelestiale.setOnClickListener(this);
        categoriaCostrutto.setOnClickListener(this);
        categoriaDrago.setOnClickListener(this);
        categoriaElementale.setOnClickListener(this);
        categoriaFolletto.setOnClickListener(this);
        categoriaGigante.setOnClickListener(this);
        categoriaImmondo.setOnClickListener(this);
        categoriaMelma.setOnClickListener(this);
        categoriaMostruosità.setOnClickListener(this);
        categoriaNonMorto.setOnClickListener(this);
        categoriaUmanoide.setOnClickListener(this);
        categoriaVegetale.setOnClickListener(this);


        tagliaEnorme.setOnClickListener(this);
        tagliaGrande.setOnClickListener(this);
        tagliaMastodontica.setOnClickListener(this);
        tagliaMedia.setOnClickListener(this);
        tagliaMinuscola.setOnClickListener(this);
        tagliaPiccola.setOnClickListener(this);
    }

    private void initAll() {
        clearFilters = findViewById(R.id.id_clear_btn);
        ambieteArtico = findViewById(R.id.ambiete_artico);
        ambieteCatacombe = findViewById(R.id.ambiete_catacombe);
        ambieteCaverna = findViewById(R.id.ambiete_caverna);
        ambieteCittatina = findViewById(R.id.ambiete_cittatina);
        ambieteCollina = findViewById(R.id.ambiete_collina);
        ambieteCosta = findViewById(R.id.ambiete_costa);
        ambieteDeserto = findViewById(R.id.ambiete_deserto);
        ambieteDungeon = findViewById(R.id.ambiete_dungeon);
        ambieteErrante = findViewById(R.id.ambiete_errante);
        ambieteForesta = findViewById(R.id.ambiete_foresta);
        ambieteGiungla = findViewById(R.id.ambiete_giungla);
        ambieteInferno = findViewById(R.id.ambiete_inferno);
        ambieteLago = findViewById(R.id.ambiete_lago);
        ambieteLimbo = findViewById(R.id.ambiete_limbo);
        ambieteMontagna = findViewById(R.id.ambiete_montagna);
        ambieteNomade = findViewById(R.id.ambiete_nomade);
        ambieteOvunque = findViewById(R.id.ambiete_ovunque);
        ambietePalude = findViewById(R.id.ambiete_palude);
        ambieteParadiso = findViewById(R.id.ambiete_paradiso);
        ambietePianoDelFuoco = findViewById(R.id.ambiete_pianoDelFuoco);
        ambietePianoDellAcqua = findViewById(R.id.ambiete_pianoDellAcqua);
        ambietePianoDellAria = findViewById(R.id.ambiete_pianoDellAria);
        ambietePianoDellaTerra = findViewById(R.id.ambiete_pianoDellaTerra);
        ambietePianura = findViewById(R.id.ambiete_pianura);
        ambieteProfonditàMarine = findViewById(R.id.ambiete_profonditàMarine);
        ambieteRovine = findViewById(R.id.ambiete_rovine);
        ambieteServitore = findViewById(R.id.ambiete_servitore);
        ambieteTerreSelvaggie = findViewById(R.id.ambiete_terreSelvaggie);
        ambieteUnderdark = findViewById(R.id.ambiete_underdark);
        ambieteVulcano = findViewById(R.id.ambiete_vulcano);

        categoriaAberrazione = findViewById(R.id.categoria_aberrazione);
        categoriaBestia = findViewById(R.id.categoria_bestia);
        categoriaCelestiale = findViewById(R.id.categoria_celestiale);
        categoriaCostrutto = findViewById(R.id.categoria_costrutto);
        categoriaDrago = findViewById(R.id.categoria_drago);
        categoriaElementale = findViewById(R.id.categoria_elementale);
        categoriaFolletto = findViewById(R.id.categoria_folletto);
        categoriaGigante = findViewById(R.id.categoria_gigante);
        categoriaImmondo = findViewById(R.id.categoria_immondo);
        categoriaMelma = findViewById(R.id.categoria_melma);
        categoriaMostruosità = findViewById(R.id.categoria_mostruosità);
        categoriaNonMorto = findViewById(R.id.categoria_nonMorto);
        categoriaUmanoide = findViewById(R.id.categoria_umanoide);
        categoriaVegetale = findViewById(R.id.categoria_vegetale);

        tagliaEnorme = findViewById(R.id.taglia_enorme);
        tagliaGrande = findViewById(R.id.taglia_grande);
        tagliaMastodontica = findViewById(R.id.taglia_mastodontica);
        tagliaMedia = findViewById(R.id.taglia_media);
        tagliaMinuscola = findViewById(R.id.taglia_minuscola);
        tagliaPiccola = findViewById(R.id.taglia_piccola);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.ambiete_artico:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("artico")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("artico");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteArtico);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("artico");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteArtico);
                }
                //finish();
                break;
            case R.id.ambiete_catacombe:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("catacombe")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("catacombe");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteCatacombe);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("catacombe");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteCatacombe);
                }
                //finish();
                break;
            case R.id.ambiete_caverna:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("caverna")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("caverna");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteCaverna);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("caverna");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteCaverna);
                }
                //finish();
                break;
            case R.id.ambiete_cittatina:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("cittatina")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("cittatina");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteCittatina);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("cittatina");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteCittatina);
                }
                //finish();
                break;
            case R.id.ambiete_collina:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("collina")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("collina");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteCollina);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("collina");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteCollina);
                }
                //finish();
                break;
            case R.id.ambiete_costa:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("costa")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("costa");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteCosta);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("costa");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteCosta);
                }
                //finish();
                break;
            case R.id.ambiete_deserto:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("deserto")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("deserto");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteDeserto);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("deserto");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteDeserto);
                }
                //finish();
                break;
            case R.id.ambiete_dungeon:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("dungeon")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("dungeon");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteDungeon);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("dungeon");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteDungeon);
                }
                //finish();
                break;
            case R.id.ambiete_errante:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("errante")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("errante");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteErrante);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("errante");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteErrante);
                }
                //finish();
                break;
            case R.id.ambiete_foresta:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("foresta")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("foresta");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteForesta);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("foresta");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteForesta);
                }
                //finish();
                break;
            case R.id.ambiete_giungla:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("giungla")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("giungla");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteGiungla);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("giungla");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteGiungla);
                }
                //finish();
                break;
            case R.id.ambiete_inferno:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("inferno")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("inferno");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteInferno);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("inferno");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteInferno);
                }
                //finish();
                break;
            case R.id.ambiete_lago:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("lago")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("lago");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteLago);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("lago");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteLago);
                }
                //finish();
                break;
            case R.id.ambiete_limbo:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("limbo")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("limbo");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteLimbo);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("limbo");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteLimbo);
                }
                //finish();
                break;
            case R.id.ambiete_montagna:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("montagna")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("montagna");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteMontagna);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("montagna");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteMontagna);
                }
                //finish();
                break;
            case R.id.ambiete_nomade:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("nomade")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("nomade");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteNomade);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("nomade");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteNomade);
                }
                //finish();
                break;
            case R.id.ambiete_ovunque:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("ovunque")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("ovunque");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteOvunque);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("ovunque");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteOvunque);
                }
                //finish();
                break;
            case R.id.ambiete_palude:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("palude")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("palude");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambietePalude);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("palude");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambietePalude);
                }
                //finish();
                break;
            case R.id.ambiete_paradiso:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("paradiso")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("paradiso");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteParadiso);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("paradiso");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteParadiso);
                }
                //finish();
                break;
            case R.id.ambiete_pianoDelFuoco:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("piano del fuoco")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("piano del fuoco");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambietePianoDelFuoco);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("piano del fuoco");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambietePianoDelFuoco);
                }
                //finish();
                break;
            case R.id.ambiete_pianoDellAcqua:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("piano dell acqua")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("piano dell acqua");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambietePianoDellAcqua);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("piano dell acqua");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambietePianoDellAcqua);
                }
                //finish();
                break;
            case R.id.ambiete_pianoDellAria:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("piano dell aria")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("piano dell aria");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambietePianoDellAria);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("piano dell aria");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambietePianoDellAria);
                }
                //finish();
                break;
            case R.id.ambiete_pianoDellaTerra:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("piano della terra")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("piano della terra");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambietePianoDellaTerra);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("piano della terra");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambietePianoDellaTerra);
                }
                //finish();
                break;
            case R.id.ambiete_pianura:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("pianura")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("pianura");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambietePianura);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("pianura");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambietePianura);
                }
                //finish();
                break;
            case R.id.ambiete_profonditàMarine:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("profondità marine")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("profondità marine");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteProfonditàMarine);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("profondità marine");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteProfonditàMarine);
                }
                //finish();
                break;
            case R.id.ambiete_rovine:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("rovine")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("rovine");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteRovine);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("rovine");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteRovine);
                }
                //finish();
                break;
            case R.id.ambiete_servitore:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("servitore")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("servitore");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteServitore);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("servitore");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteServitore);
                }
                //finish();
                break;
            case R.id.ambiete_terreSelvaggie:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("terre selvaggie")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("terre selvaggie");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteTerreSelvaggie);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("terre selvaggie");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteTerreSelvaggie);
                }
                //finish();
                break;
            case R.id.ambiete_underdark:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("underdark")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("underdark");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteUnderdark);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("underdark");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteUnderdark);
                }
                //finish();
                break;
            case R.id.ambiete_vulcano:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains("vulcano")) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove("vulcano");
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteVulcano);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add("vulcano");
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteVulcano);
                }
                //finish();
                break;
            case R.id.categoria_aberrazione:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains("aberrazione")) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove("aberrazione");
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaAberrazione);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add("aberrazione");
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaAberrazione);
                }
                //finish();
                break;
            case R.id.categoria_bestia:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains("bestia")) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove("bestia");
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaBestia);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add("bestia");
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaBestia);
                }
                //finish();
                break;
            case R.id.categoria_celestiale:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains("celestiale")) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove("celestiale");
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaCelestiale);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add("celestiale");
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaCelestiale);
                }
                //finish();
                break;
            case R.id.categoria_costrutto:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains("costrutto")) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove("costrutto");
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaCostrutto);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add("costrutto");
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaCostrutto);
                }
                //finish();
                break;
            case R.id.categoria_drago:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains("drago")) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove("drago");
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaDrago);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add("drago");
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaDrago);
                }
                //finish();
                break;
            case R.id.categoria_elementale:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains("elementale")) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove("elementale");
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaElementale);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add("elementale");
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaElementale);
                }
                //finish();
                break;
            case R.id.categoria_folletto:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains("folletto")) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove("folletto");
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaFolletto);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add("folletto");
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaFolletto);
                }
                //finish();
                break;
            case R.id.categoria_gigante:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains("gigante")) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove("gigante");
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaGigante);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add("gigante");
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaGigante);
                }
                //finish();
                break;
            case R.id.categoria_immondo:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains("immondo")) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove("immondo");
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaImmondo);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add("immondo");
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaImmondo);
                }
                //finish();
                break;
            case R.id.categoria_melma:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains("melma")) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove("melma");
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaMelma);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add("melma");
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaMelma);
                }
                //finish();
                break;
            case R.id.categoria_mostruosità:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains("mostruosità")) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove("mostruosità");
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaMostruosità);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add("mostruosità");
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaMostruosità);
                }
                //finish();
                break;
            case R.id.categoria_nonMorto:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains("non morto")) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove("non morto");
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaNonMorto);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add("non morto");
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaNonMorto);
                }
                //finish();
                break;
            case R.id.categoria_umanoide:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains("umanoide")) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove("umanoide");
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaUmanoide);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add("umanoide");
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaUmanoide);
                }
                //finish();
                break;
            case R.id.categoria_vegetale:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains("vegetale")) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove("vegetale");
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaVegetale);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add("vegetale");
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaVegetale);
                }
                //finish();
                break;
            case R.id.taglia_enorme:
                if (SearchMonstersActivity.selectedTagliaFilters.contains("enorme")) {
                    SearchMonstersActivity.selectedTagliaFilters.remove("enorme");
                    if (SearchMonstersActivity.selectedTagliaFilters.size() == 0) {
                        SearchMonstersActivity.isTagliaSelected = false;
                    }
                    lookDeselected(tagliaEnorme);
                }else {
                    SearchMonstersActivity.selectedTagliaFilters.add("enorme");
                    SearchMonstersActivity.isTagliaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(tagliaEnorme);
                }
                //finish();
                break;
            case R.id.taglia_grande:
                if (SearchMonstersActivity.selectedTagliaFilters.contains("grande")) {
                    SearchMonstersActivity.selectedTagliaFilters.remove("grande");
                    if (SearchMonstersActivity.selectedTagliaFilters.size() == 0) {
                        SearchMonstersActivity.isTagliaSelected = false;
                    }
                    lookDeselected(tagliaGrande);
                }else {
                    SearchMonstersActivity.selectedTagliaFilters.add("grande");
                    SearchMonstersActivity.isTagliaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(tagliaGrande);
                }
                //finish();
                break;
            case R.id.taglia_mastodontica:
                if (SearchMonstersActivity.selectedTagliaFilters.contains("mastodontica")) {
                    SearchMonstersActivity.selectedTagliaFilters.remove("mastodontica");
                    if (SearchMonstersActivity.selectedTagliaFilters.size() == 0) {
                        SearchMonstersActivity.isTagliaSelected = false;
                    }
                    lookDeselected(tagliaMastodontica);
                }else {
                    SearchMonstersActivity.selectedTagliaFilters.add("mastodontica");
                    SearchMonstersActivity.isTagliaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(tagliaMastodontica);
                }
                //finish();
                break;
            case R.id.taglia_media:
                if (SearchMonstersActivity.selectedTagliaFilters.contains("media")) {
                    SearchMonstersActivity.selectedTagliaFilters.remove("media");
                    if (SearchMonstersActivity.selectedTagliaFilters.size() == 0) {
                        SearchMonstersActivity.isTagliaSelected = false;
                    }
                    lookDeselected(tagliaMedia);
                }else {
                    SearchMonstersActivity.selectedTagliaFilters.add("media");
                    SearchMonstersActivity.isTagliaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(tagliaMedia);
                }
                //finish();
                break;
            case R.id.taglia_minuscola:
                if (SearchMonstersActivity.selectedTagliaFilters.contains("minuscola")) {
                    SearchMonstersActivity.selectedTagliaFilters.remove("minuscola");
                    if (SearchMonstersActivity.selectedTagliaFilters.size() == 0) {
                        SearchMonstersActivity.isTagliaSelected = false;
                    }
                    lookDeselected(tagliaMinuscola);
                }else {
                    SearchMonstersActivity.selectedTagliaFilters.add("minuscola");
                    SearchMonstersActivity.isTagliaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(tagliaMinuscola);
                }
                //finish();
                break;
            case R.id.taglia_piccola:
                if (SearchMonstersActivity.selectedTagliaFilters.contains("piccola")) {
                    SearchMonstersActivity.selectedTagliaFilters.remove("piccola");
                    if (SearchMonstersActivity.selectedTagliaFilters.size() == 0) {
                        SearchMonstersActivity.isTagliaSelected = false;
                    }
                    lookDeselected(tagliaPiccola);
                }else {
                    SearchMonstersActivity.selectedTagliaFilters.add("piccola");
                    SearchMonstersActivity.isTagliaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(tagliaPiccola);
                }
                //finish();
                break;
            case R.id.id_clear_btn:
                //clearClicked();
                SearchMonstersActivity.isFiltersApplied = true;
                finish();
                break;
        }
    }

    private void initColors()
    {
        white = ContextCompat.getColor(getApplicationContext(), R.color.white);
        rossoPorpora = ContextCompat.getColor(getApplicationContext(), R.color.rossoPorpora);
        cream = ContextCompat.getColor(getApplicationContext(), R.color.creamColor);
    }

    private void lookSelected(LinearLayout selectedFilter)
    {
        selectedFilter.setBackgroundColor(rossoPorpora);
    }

    private void lookDeselected(LinearLayout selectedFilter)
    {
        selectedFilter.setBackgroundColor(white);
    }
}