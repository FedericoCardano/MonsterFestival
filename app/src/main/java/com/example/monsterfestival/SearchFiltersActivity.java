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
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.artico))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.artico));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteArtico);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.artico));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteArtico);
                }
                //finish();
                break;
            case R.id.ambiete_catacombe:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.catacombe))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.catacombe));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteCatacombe);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.catacombe));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteCatacombe);
                }
                //finish();
                break;
            case R.id.ambiete_caverna:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.caverna))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.caverna));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteCaverna);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.caverna));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteCaverna);
                }
                //finish();
                break;
            case R.id.ambiete_cittatina:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.cittatina))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.cittatina));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteCittatina);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.cittatina));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteCittatina);
                }
                //finish();
                break;
            case R.id.ambiete_collina:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.collina))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.collina));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteCollina);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.collina));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteCollina);
                }
                //finish();
                break;
            case R.id.ambiete_costa:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.costa))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.costa));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteCosta);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.costa));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteCosta);
                }
                //finish();
                break;
            case R.id.ambiete_deserto:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.deserto))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.deserto));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteDeserto);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.deserto));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteDeserto);
                }
                //finish();
                break;
            case R.id.ambiete_dungeon:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.dungeon))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.dungeon));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteDungeon);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.dungeon));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteDungeon);
                }
                //finish();
                break;
            case R.id.ambiete_errante:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.errante))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.errante));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteErrante);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.errante));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteErrante);
                }
                //finish();
                break;
            case R.id.ambiete_foresta:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.foresta))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.foresta));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteForesta);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.foresta));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteForesta);
                }
                //finish();
                break;
            case R.id.ambiete_giungla:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.giungla))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.giungla));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteGiungla);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.giungla));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteGiungla);
                }
                //finish();
                break;
            case R.id.ambiete_inferno:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.inferno))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.inferno));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteInferno);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.inferno));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteInferno);
                }
                //finish();
                break;
            case R.id.ambiete_lago:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.lago))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.lago));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteLago);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.lago));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteLago);
                }
                //finish();
                break;
            case R.id.ambiete_limbo:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.limbo))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.limbo));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteLimbo);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.limbo));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteLimbo);
                }
                //finish();
                break;
            case R.id.ambiete_montagna:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.montagna))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.montagna));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteMontagna);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.montagna));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteMontagna);
                }
                //finish();
                break;
            case R.id.ambiete_nomade:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.nomade))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.nomade));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteNomade);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.nomade));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteNomade);
                }
                //finish();
                break;
            case R.id.ambiete_ovunque:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.ovunque))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.ovunque));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteOvunque);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.ovunque));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteOvunque);
                }
                //finish();
                break;
            case R.id.ambiete_palude:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.palude))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.palude));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambietePalude);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.palude));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambietePalude);
                }
                //finish();
                break;
            case R.id.ambiete_paradiso:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.paradiso))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.paradiso));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteParadiso);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.paradiso));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteParadiso);
                }
                //finish();
                break;
            case R.id.ambiete_pianoDelFuoco:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.piano_del_fuoco))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.piano_del_fuoco));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambietePianoDelFuoco);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.piano_del_fuoco));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambietePianoDelFuoco);
                }
                //finish();
                break;
            case R.id.ambiete_pianoDellAcqua:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.piano_dell_acqua))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.piano_dell_acqua));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambietePianoDellAcqua);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.piano_dell_acqua));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambietePianoDellAcqua);
                }
                //finish();
                break;
            case R.id.ambiete_pianoDellAria:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.piano_dell_aria))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.piano_dell_aria));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambietePianoDellAria);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.piano_dell_aria));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambietePianoDellAria);
                }
                //finish();
                break;
            case R.id.ambiete_pianoDellaTerra:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.piano_della_terra))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.piano_della_terra));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambietePianoDellaTerra);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.piano_della_terra));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambietePianoDellaTerra);
                }
                //finish();
                break;
            case R.id.ambiete_pianura:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.pianura))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.pianura));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambietePianura);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.pianura));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambietePianura);
                }
                //finish();
                break;
            case R.id.ambiete_profonditàMarine:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.profondit_marine))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.profondit_marine));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteProfonditàMarine);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.profondit_marine));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteProfonditàMarine);
                }
                //finish();
                break;
            case R.id.ambiete_rovine:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.rovine))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.rovine));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteRovine);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.rovine));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteRovine);
                }
                //finish();
                break;
            case R.id.ambiete_servitore:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.servitore))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.servitore));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteServitore);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.servitore));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteServitore);
                }
                //finish();
                break;
            case R.id.ambiete_terreSelvaggie:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.terre_selvaggie))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.terre_selvaggie));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteTerreSelvaggie);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.terre_selvaggie));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteTerreSelvaggie);
                }
                //finish();
                break;
            case R.id.ambiete_underdark:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.underdark))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.underdark));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteUnderdark);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.underdark));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteUnderdark);
                }
                //finish();
                break;
            case R.id.ambiete_vulcano:
                if (SearchMonstersActivity.selectedAmbieteFilters.contains(getResources().getString(R.string.vulcano))) {
                    SearchMonstersActivity.selectedAmbieteFilters.remove(getResources().getString(R.string.vulcano));
                    if (SearchMonstersActivity.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersActivity.isAmbieteSelected = false;
                    }
                    lookDeselected(ambieteVulcano);
                }else {
                    SearchMonstersActivity.selectedAmbieteFilters.add(getResources().getString(R.string.vulcano));
                    SearchMonstersActivity.isAmbieteSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(ambieteVulcano);
                }
                //finish();
                break;
            case R.id.categoria_aberrazione:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains(getResources().getString(R.string.aberrazione))) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove(getResources().getString(R.string.aberrazione));
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaAberrazione);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add(getResources().getString(R.string.aberrazione));
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaAberrazione);
                }
                //finish();
                break;
            case R.id.categoria_bestia:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains(getResources().getString(R.string.bestia))) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove(getResources().getString(R.string.bestia));
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaBestia);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add(getResources().getString(R.string.bestia));
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaBestia);
                }
                //finish();
                break;
            case R.id.categoria_celestiale:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains(getResources().getString(R.string.celestiale))) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove(getResources().getString(R.string.celestiale));
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaCelestiale);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add(getResources().getString(R.string.celestiale));
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaCelestiale);
                }
                //finish();
                break;
            case R.id.categoria_costrutto:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains(getResources().getString(R.string.costrutto))) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove(getResources().getString(R.string.costrutto));
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaCostrutto);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add(getResources().getString(R.string.costrutto));
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaCostrutto);
                }
                //finish();
                break;
            case R.id.categoria_drago:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains(getResources().getString(R.string.drago))) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove(getResources().getString(R.string.drago));
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaDrago);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add(getResources().getString(R.string.drago));
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaDrago);
                }
                //finish();
                break;
            case R.id.categoria_elementale:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains(getResources().getString(R.string.elementale))) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove(getResources().getString(R.string.elementale));
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaElementale);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add(getResources().getString(R.string.elementale));
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaElementale);
                }
                //finish();
                break;
            case R.id.categoria_folletto:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains(getResources().getString(R.string.folletto))) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove(getResources().getString(R.string.folletto));
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaFolletto);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add(getResources().getString(R.string.folletto));
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaFolletto);
                }
                //finish();
                break;
            case R.id.categoria_gigante:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains(getResources().getString(R.string.gigante))) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove(getResources().getString(R.string.gigante));
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaGigante);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add(getResources().getString(R.string.gigante));
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaGigante);
                }
                //finish();
                break;
            case R.id.categoria_immondo:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains(getResources().getString(R.string.immondo))) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove(getResources().getString(R.string.immondo));
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaImmondo);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add(getResources().getString(R.string.immondo));
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaImmondo);
                }
                //finish();
                break;
            case R.id.categoria_melma:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains(getResources().getString(R.string.melma))) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove(getResources().getString(R.string.melma));
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaMelma);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add(getResources().getString(R.string.melma));
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaMelma);
                }
                //finish();
                break;
            case R.id.categoria_mostruosità:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains(getResources().getString(R.string.mostruosit))) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove(getResources().getString(R.string.mostruosit));
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaMostruosità);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add(getResources().getString(R.string.mostruosit));
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaMostruosità);
                }
                //finish();
                break;
            case R.id.categoria_nonMorto:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains(getResources().getString(R.string.non_morto))) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove(getResources().getString(R.string.non_morto));
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaNonMorto);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add(getResources().getString(R.string.non_morto));
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaNonMorto);
                }
                //finish();
                break;
            case R.id.categoria_umanoide:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains(getResources().getString(R.string.umanoide))) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove(getResources().getString(R.string.umanoide));
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaUmanoide);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add(getResources().getString(R.string.umanoide));
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaUmanoide);
                }
                //finish();
                break;
            case R.id.categoria_vegetale:
                if (SearchMonstersActivity.selectedCategoriaFilters.contains(getResources().getString(R.string.vegetale))) {
                    SearchMonstersActivity.selectedCategoriaFilters.remove(getResources().getString(R.string.vegetale));
                    if (SearchMonstersActivity.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersActivity.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaVegetale);
                }else {
                    SearchMonstersActivity.selectedCategoriaFilters.add(getResources().getString(R.string.vegetale));
                    SearchMonstersActivity.isCategoriaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(categoriaVegetale);
                }
                //finish();
                break;
            case R.id.taglia_enorme:
                if (SearchMonstersActivity.selectedTagliaFilters.contains(getResources().getString(R.string.enorme))) {
                    SearchMonstersActivity.selectedTagliaFilters.remove(getResources().getString(R.string.enorme));
                    if (SearchMonstersActivity.selectedTagliaFilters.size() == 0) {
                        SearchMonstersActivity.isTagliaSelected = false;
                    }
                    lookDeselected(tagliaEnorme);
                }else {
                    SearchMonstersActivity.selectedTagliaFilters.add(getResources().getString(R.string.enorme));
                    SearchMonstersActivity.isTagliaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(tagliaEnorme);
                }
                //finish();
                break;
            case R.id.taglia_grande:
                if (SearchMonstersActivity.selectedTagliaFilters.contains(getResources().getString(R.string.grande))) {
                    SearchMonstersActivity.selectedTagliaFilters.remove(getResources().getString(R.string.grande));
                    if (SearchMonstersActivity.selectedTagliaFilters.size() == 0) {
                        SearchMonstersActivity.isTagliaSelected = false;
                    }
                    lookDeselected(tagliaGrande);
                }else {
                    SearchMonstersActivity.selectedTagliaFilters.add(getResources().getString(R.string.grande));
                    SearchMonstersActivity.isTagliaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(tagliaGrande);
                }
                //finish();
                break;
            case R.id.taglia_mastodontica:
                if (SearchMonstersActivity.selectedTagliaFilters.contains(getResources().getString(R.string.mastodontica))) {
                    SearchMonstersActivity.selectedTagliaFilters.remove(getResources().getString(R.string.mastodontica));
                    if (SearchMonstersActivity.selectedTagliaFilters.size() == 0) {
                        SearchMonstersActivity.isTagliaSelected = false;
                    }
                    lookDeselected(tagliaMastodontica);
                }else {
                    SearchMonstersActivity.selectedTagliaFilters.add(getResources().getString(R.string.mastodontica));
                    SearchMonstersActivity.isTagliaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(tagliaMastodontica);
                }
                //finish();
                break;
            case R.id.taglia_media:
                if (SearchMonstersActivity.selectedTagliaFilters.contains(getResources().getString(R.string.media))) {
                    SearchMonstersActivity.selectedTagliaFilters.remove(getResources().getString(R.string.media));
                    if (SearchMonstersActivity.selectedTagliaFilters.size() == 0) {
                        SearchMonstersActivity.isTagliaSelected = false;
                    }
                    lookDeselected(tagliaMedia);
                }else {
                    SearchMonstersActivity.selectedTagliaFilters.add(getResources().getString(R.string.media));
                    SearchMonstersActivity.isTagliaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(tagliaMedia);
                }
                //finish();
                break;
            case R.id.taglia_minuscola:
                if (SearchMonstersActivity.selectedTagliaFilters.contains(getResources().getString(R.string.minuscola))) {
                    SearchMonstersActivity.selectedTagliaFilters.remove(getResources().getString(R.string.minuscola));
                    if (SearchMonstersActivity.selectedTagliaFilters.size() == 0) {
                        SearchMonstersActivity.isTagliaSelected = false;
                    }
                    lookDeselected(tagliaMinuscola);
                }else {
                    SearchMonstersActivity.selectedTagliaFilters.add(getResources().getString(R.string.minuscola));
                    SearchMonstersActivity.isTagliaSelected = true;
                    //SearchMonstersActivity.isFiltersApplied = true;
                    lookSelected(tagliaMinuscola);
                }
                //finish();
                break;
            case R.id.taglia_piccola:
                if (SearchMonstersActivity.selectedTagliaFilters.contains(getResources().getString(R.string.piccola))) {
                    SearchMonstersActivity.selectedTagliaFilters.remove(getResources().getString(R.string.piccola));
                    if (SearchMonstersActivity.selectedTagliaFilters.size() == 0) {
                        SearchMonstersActivity.isTagliaSelected = false;
                    }
                    lookDeselected(tagliaPiccola);
                }else {
                    SearchMonstersActivity.selectedTagliaFilters.add(getResources().getString(R.string.piccola));
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