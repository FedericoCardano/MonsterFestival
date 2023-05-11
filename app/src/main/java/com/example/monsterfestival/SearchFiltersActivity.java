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
                SearchMonstersActivity.selectedAmbieteFilters.add("artico");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteArtico);
                //finish();
                break;
            case R.id.ambiete_catacombe:
                SearchMonstersActivity.selectedAmbieteFilters.add("catacombe");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteCatacombe);
                //finish();
                break;
            case R.id.ambiete_caverna:
                SearchMonstersActivity.selectedAmbieteFilters.add("caverna");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteCaverna);
                //finish();
                break;
            case R.id.ambiete_cittatina:
                SearchMonstersActivity.selectedAmbieteFilters.add("cittatina");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteCittatina);
                //finish();
                break;
            case R.id.ambiete_collina:
                SearchMonstersActivity.selectedAmbieteFilters.add("collina");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteCollina);
                //finish();
                break;
            case R.id.ambiete_costa:
                SearchMonstersActivity.selectedAmbieteFilters.add("costa");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteCosta);
                //finish();
                break;
            case R.id.ambiete_deserto:
                SearchMonstersActivity.selectedAmbieteFilters.add("deserto");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteDeserto);
                //finish();
                break;
            case R.id.ambiete_dungeon:
                SearchMonstersActivity.selectedAmbieteFilters.add("dungeon");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteDungeon);
                //finish();
                break;
            case R.id.ambiete_errante:
                SearchMonstersActivity.selectedAmbieteFilters.add("errante");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteErrante);
                //finish();
                break;
            case R.id.ambiete_foresta:
                SearchMonstersActivity.selectedAmbieteFilters.add("foresta");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteForesta);
                //finish();
                break;
            case R.id.ambiete_giungla:
                SearchMonstersActivity.selectedAmbieteFilters.add("giungla");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteGiungla);
                //finish();
                break;
            case R.id.ambiete_inferno:
                SearchMonstersActivity.selectedAmbieteFilters.add("inferno");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteInferno);
                //finish();
                break;
            case R.id.ambiete_lago:
                SearchMonstersActivity.selectedAmbieteFilters.add("lago");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteLago);
                //finish();
                break;
            case R.id.ambiete_limbo:
                SearchMonstersActivity.selectedAmbieteFilters.add("limbo");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteLimbo);
                //finish();
                break;
            case R.id.ambiete_montagna:
                SearchMonstersActivity.selectedAmbieteFilters.add("montagna");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteMontagna);
                //finish();
                break;
            case R.id.ambiete_nomade:
                SearchMonstersActivity.selectedAmbieteFilters.add("nomade");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteNomade);
                //finish();
                break;
            case R.id.ambiete_ovunque:
                SearchMonstersActivity.selectedAmbieteFilters.add("ovunque");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteOvunque);
                //finish();
                break;
            case R.id.ambiete_palude:
                SearchMonstersActivity.selectedAmbieteFilters.add("palude");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambietePalude);
                //finish();
                break;
            case R.id.ambiete_paradiso:
                SearchMonstersActivity.selectedAmbieteFilters.add("paradiso");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteParadiso);
                //finish();
                break;
            case R.id.ambiete_pianoDelFuoco:
                SearchMonstersActivity.selectedAmbieteFilters.add("piano del fuoco");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambietePianoDelFuoco);
                //finish();
                break;
            case R.id.ambiete_pianoDellAcqua:
                SearchMonstersActivity.selectedAmbieteFilters.add("piano dell acqua");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambietePianoDellAcqua);
                //finish();
                break;
            case R.id.ambiete_pianoDellAria:
                SearchMonstersActivity.selectedAmbieteFilters.add("piano dell aria");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambietePianoDellAria);
                //finish();
                break;
            case R.id.ambiete_pianoDellaTerra:
                SearchMonstersActivity.selectedAmbieteFilters.add("piano della terra");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambietePianoDellaTerra);
                //finish();
                break;
            case R.id.ambiete_pianura:
                SearchMonstersActivity.selectedAmbieteFilters.add("pianura");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambietePianura);
                //finish();
                break;
            case R.id.ambiete_profonditàMarine:
                SearchMonstersActivity.selectedAmbieteFilters.add("profondità marine");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteProfonditàMarine);
                //finish();
                break;
            case R.id.ambiete_rovine:
                SearchMonstersActivity.selectedAmbieteFilters.add("rovine");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteRovine);
                //finish();
                break;
            case R.id.ambiete_servitore:
                SearchMonstersActivity.selectedAmbieteFilters.add("servitore");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteServitore);
                //finish();
                break;
            case R.id.ambiete_terreSelvaggie:
                SearchMonstersActivity.selectedAmbieteFilters.add("terre selvaggie");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteTerreSelvaggie);
                //finish();
                break;
            case R.id.ambiete_underdark:
                SearchMonstersActivity.selectedAmbieteFilters.add("underdark");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteUnderdark);
                //finish();
                break;
            case R.id.ambiete_vulcano:
                SearchMonstersActivity.selectedAmbieteFilters.add("vulcano");
                SearchMonstersActivity.isAmbieteSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(ambieteVulcano);
                //finish();
                break;
            case R.id.categoria_aberrazione:
                SearchMonstersActivity.selectedCategoriaFilters.add("aberrazione");
                SearchMonstersActivity.isCategoriaSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(categoriaAberrazione);
                //finish();
                break;
            case R.id.categoria_bestia:
                SearchMonstersActivity.selectedCategoriaFilters.add("bestia");
                SearchMonstersActivity.isCategoriaSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(categoriaBestia);
                //finish();
                break;
            case R.id.categoria_celestiale:
                SearchMonstersActivity.selectedCategoriaFilters.add("celestiale");
                SearchMonstersActivity.isCategoriaSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(categoriaCelestiale);
                //finish();
                break;
            case R.id.categoria_costrutto:
                SearchMonstersActivity.selectedCategoriaFilters.add("costrutto");
                SearchMonstersActivity.isCategoriaSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(categoriaCostrutto);
                //finish();
                break;
            case R.id.categoria_drago:
                SearchMonstersActivity.selectedCategoriaFilters.add("drago");
                SearchMonstersActivity.isCategoriaSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(categoriaDrago);
                //finish();
                break;
            case R.id.categoria_elementale:
                SearchMonstersActivity.selectedCategoriaFilters.add("elementale");
                SearchMonstersActivity.isCategoriaSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(categoriaElementale);
                //finish();
                break;
            case R.id.categoria_folletto:
                SearchMonstersActivity.selectedCategoriaFilters.add("folletto");
                SearchMonstersActivity.isCategoriaSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(categoriaFolletto);
                //finish();
                break;
            case R.id.categoria_gigante:
                SearchMonstersActivity.selectedCategoriaFilters.add("gigante");
                SearchMonstersActivity.isCategoriaSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(categoriaGigante);
                //finish();
                break;
            case R.id.categoria_immondo:
                SearchMonstersActivity.selectedCategoriaFilters.add("immondo");
                SearchMonstersActivity.isCategoriaSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(categoriaImmondo);
                //finish();
                break;
            case R.id.categoria_melma:
                SearchMonstersActivity.selectedCategoriaFilters.add("melma");
                SearchMonstersActivity.isCategoriaSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(categoriaMelma);
                //finish();
                break;
            case R.id.categoria_mostruosità:
                SearchMonstersActivity.selectedCategoriaFilters.add("mostruosità");
                SearchMonstersActivity.isCategoriaSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(categoriaMostruosità);
                //finish();
                break;
            case R.id.categoria_nonMorto:
                SearchMonstersActivity.selectedCategoriaFilters.add("non morto");
                SearchMonstersActivity.isCategoriaSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(categoriaNonMorto);
                //finish();
                break;
            case R.id.categoria_umanoide:
                SearchMonstersActivity.selectedTagliaFilters.add("umanoide");
                SearchMonstersActivity.isTagliaSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(categoriaUmanoide);
                //finish();
                break;
            case R.id.categoria_vegetale:
                SearchMonstersActivity.selectedTagliaFilters.add("vegetale");
                SearchMonstersActivity.isTagliaSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(categoriaVegetale);
                //finish();
                break;
            case R.id.taglia_enorme:
                SearchMonstersActivity.selectedTagliaFilters.add("enorme");
                SearchMonstersActivity.isTagliaSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(tagliaEnorme);
                //finish();
                break;
            case R.id.taglia_grande:
                SearchMonstersActivity.selectedTagliaFilters.add("grande");
                SearchMonstersActivity.isTagliaSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(tagliaGrande);
                //finish();
                break;
            case R.id.taglia_mastodontica:
                SearchMonstersActivity.selectedTagliaFilters.add("mastodontica");
                SearchMonstersActivity.isTagliaSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(tagliaMastodontica);
                //finish();
                break;
            case R.id.taglia_media:
                SearchMonstersActivity.selectedTagliaFilters.add("media");
                SearchMonstersActivity.isTagliaSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(tagliaMedia);
                //finish();
                break;
            case R.id.taglia_minuscola:
                SearchMonstersActivity.selectedTagliaFilters.add("minuscola");
                SearchMonstersActivity.isTagliaSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(tagliaMinuscola);
                //finish();
                break;
            case R.id.taglia_piccola:
                SearchMonstersActivity.selectedTagliaFilters.add("piccola");
                SearchMonstersActivity.isTagliaSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(tagliaPiccola);
                //finish();
                break;
            /*case R.id.ali_LawfulGood:
                SearchMonstersActivity.selectedAlignmentFilters.add("Lawful Good");
                SearchMonstersActivity.isAlignmentSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(alignmentLawfulGood);
                //finish();
                break;
            case R.id.ali_NeutralGood:
                SearchMonstersActivity.selectedAlignmentFilters.add("Neutral Good");
                SearchMonstersActivity.isAlignmentSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(alignmentNeutralGood);
                //finish();
                break;
            case R.id.ali_ChaoticGood:
                SearchMonstersActivity.selectedAlignmentFilters.add("Chaotic Good");
                SearchMonstersActivity.isAlignmentSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(alignmentChaoticGood);
                //finish();
                break;
            case R.id.ali_LawfulNeutral:
                SearchMonstersActivity.selectedAlignmentFilters.add("Lawful Neutral");
                SearchMonstersActivity.isAlignmentSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(alignmentLawfulNeutral);
                //finish();
                break;
            case R.id.ali_Neutral:
                SearchMonstersActivity.selectedAlignmentFilters.add("Neutral");
                SearchMonstersActivity.isAlignmentSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(alignmentNeutral);
                //finish();
                break;
            case R.id.ali_ChaoticNeutral:
                SearchMonstersActivity.selectedAlignmentFilters.add("Chaotic Neutral");
                SearchMonstersActivity.isAlignmentSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(alignmentChaoticNeutral);
                //finish();
                break;
            case R.id.ali_LawfulEvil:
                SearchMonstersActivity.selectedAlignmentFilters.add("Lawful Evil");
                SearchMonstersActivity.isAlignmentSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(alignmentLawfulEvil);
                //finish();
                break;
            case R.id.ali_NeutralEvil:
                SearchMonstersActivity.selectedAlignmentFilters.add("Neutral Evil");
                SearchMonstersActivity.isAlignmentSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(alignmentNeutralEvil);
                //finish();
                break;
            case R.id.ali_ChaoticEvil:
                SearchMonstersActivity.selectedAlignmentFilters.add("Chaotic Evil");
                SearchMonstersActivity.isAlignmentSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(alignmentChaoticEvil);
                //finish();
                break;*/
            case R.id.id_clear_btn:
                //clearClicked();
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
}