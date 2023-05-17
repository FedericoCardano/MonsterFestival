package com.example.monsterfestival;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class SearchFiltersFragment extends Fragment implements View.OnClickListener {

    View rootView;
    private TextView clearFilters;
    private LinearLayout ambieteArtico, ambieteCatacombe, ambieteCaverna, ambieteCittatina, ambieteCollina, ambieteCosta, ambieteDeserto, ambieteDungeon, ambieteErrante, ambieteForesta, ambieteGiungla, ambieteInferno, ambieteLago, ambieteLimbo, ambieteMontagna, ambieteNomade, ambieteOvunque, ambietePalude, ambieteParadiso, ambietePianoDelFuoco, ambietePianoDellAcqua, ambietePianoDellAria, ambietePianoDellaTerra, ambietePianura, ambieteProfonditàMarine, ambieteRovine, ambieteServitore, ambieteTerreSelvaggie, ambieteUnderdark, ambieteVulcano;
    private LinearLayout categoriaAberrazione, categoriaBestia, categoriaCelestiale, categoriaCostrutto, categoriaDrago, categoriaElementale, categoriaFolletto, categoriaGigante, categoriaImmondo, categoriaMelma, categoriaMostruosità, categoriaNonMorto, categoriaUmanoide, categoriaVegetale;
    private LinearLayout tagliaEnorme, tagliaGrande, tagliaMastodontica, tagliaMedia, tagliaMinuscola, tagliaPiccola;

    private int white, rossoPorpora, cream;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search_filters, container, false);

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
        return rootView;
    }

    private void initAll() {
        clearFilters = rootView.findViewById(R.id.id_clear_btn);
        ambieteArtico = rootView.findViewById(R.id.ambiete_artico);
        ambieteCatacombe = rootView.findViewById(R.id.ambiete_catacombe);
        ambieteCaverna = rootView.findViewById(R.id.ambiete_caverna);
        ambieteCittatina = rootView.findViewById(R.id.ambiete_cittatina);
        ambieteCollina = rootView.findViewById(R.id.ambiete_collina);
        ambieteCosta = rootView.findViewById(R.id.ambiete_costa);
        ambieteDeserto = rootView.findViewById(R.id.ambiete_deserto);
        ambieteDungeon = rootView.findViewById(R.id.ambiete_dungeon);
        ambieteErrante = rootView.findViewById(R.id.ambiete_errante);
        ambieteForesta = rootView.findViewById(R.id.ambiete_foresta);
        ambieteGiungla = rootView.findViewById(R.id.ambiete_giungla);
        ambieteInferno = rootView.findViewById(R.id.ambiete_inferno);
        ambieteLago = rootView.findViewById(R.id.ambiete_lago);
        ambieteLimbo = rootView.findViewById(R.id.ambiete_limbo);
        ambieteMontagna = rootView.findViewById(R.id.ambiete_montagna);
        ambieteNomade = rootView.findViewById(R.id.ambiete_nomade);
        ambieteOvunque = rootView.findViewById(R.id.ambiete_ovunque);
        ambietePalude = rootView.findViewById(R.id.ambiete_palude);
        ambieteParadiso = rootView.findViewById(R.id.ambiete_paradiso);
        ambietePianoDelFuoco = rootView.findViewById(R.id.ambiete_pianoDelFuoco);
        ambietePianoDellAcqua = rootView.findViewById(R.id.ambiete_pianoDellAcqua);
        ambietePianoDellAria = rootView.findViewById(R.id.ambiete_pianoDellAria);
        ambietePianoDellaTerra = rootView.findViewById(R.id.ambiete_pianoDellaTerra);
        ambietePianura = rootView.findViewById(R.id.ambiete_pianura);
        ambieteProfonditàMarine = rootView.findViewById(R.id.ambiete_profonditàMarine);
        ambieteRovine = rootView.findViewById(R.id.ambiete_rovine);
        ambieteServitore = rootView.findViewById(R.id.ambiete_servitore);
        ambieteTerreSelvaggie = rootView.findViewById(R.id.ambiete_terreSelvaggie);
        ambieteUnderdark = rootView.findViewById(R.id.ambiete_underdark);
        ambieteVulcano = rootView.findViewById(R.id.ambiete_vulcano);

        categoriaAberrazione = rootView.findViewById(R.id.categoria_aberrazione);
        categoriaBestia = rootView.findViewById(R.id.categoria_bestia);
        categoriaCelestiale = rootView.findViewById(R.id.categoria_celestiale);
        categoriaCostrutto = rootView.findViewById(R.id.categoria_costrutto);
        categoriaDrago = rootView.findViewById(R.id.categoria_drago);
        categoriaElementale = rootView.findViewById(R.id.categoria_elementale);
        categoriaFolletto = rootView.findViewById(R.id.categoria_folletto);
        categoriaGigante = rootView.findViewById(R.id.categoria_gigante);
        categoriaImmondo = rootView.findViewById(R.id.categoria_immondo);
        categoriaMelma = rootView.findViewById(R.id.categoria_melma);
        categoriaMostruosità = rootView.findViewById(R.id.categoria_mostruosità);
        categoriaNonMorto = rootView.findViewById(R.id.categoria_nonMorto);
        categoriaUmanoide = rootView.findViewById(R.id.categoria_umanoide);
        categoriaVegetale = rootView.findViewById(R.id.categoria_vegetale);

        tagliaEnorme = rootView.findViewById(R.id.taglia_enorme);
        tagliaGrande = rootView.findViewById(R.id.taglia_grande);
        tagliaMastodontica = rootView.findViewById(R.id.taglia_mastodontica);
        tagliaMedia = rootView.findViewById(R.id.taglia_media);
        tagliaMinuscola = rootView.findViewById(R.id.taglia_minuscola);
        tagliaPiccola = rootView.findViewById(R.id.taglia_piccola);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.ambiete_artico:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.artico))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.artico));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteArtico);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.artico));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteArtico);
                }
                break;
            case R.id.ambiete_catacombe:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.catacombe))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.catacombe));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteCatacombe);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.catacombe));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteCatacombe);
                }
                break;
            case R.id.ambiete_caverna:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.caverna))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.caverna));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteCaverna);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.caverna));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteCaverna);
                }
                break;
            case R.id.ambiete_cittatina:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.cittatina))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.cittatina));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteCittatina);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.cittatina));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteCittatina);
                }
                break;
            case R.id.ambiete_collina:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.collina))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.collina));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteCollina);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.collina));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteCollina);
                }
                break;
            case R.id.ambiete_costa:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.costa))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.costa));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteCosta);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.costa));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteCosta);
                }
                break;
            case R.id.ambiete_deserto:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.deserto))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.deserto));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteDeserto);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.deserto));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteDeserto);
                }
                break;
            case R.id.ambiete_dungeon:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.dungeon))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.dungeon));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteDungeon);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.dungeon));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteDungeon);
                }
                break;
            case R.id.ambiete_errante:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.errante))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.errante));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteErrante);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.errante));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteErrante);
                }
                break;
            case R.id.ambiete_foresta:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.foresta))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.foresta));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteForesta);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.foresta));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteForesta);
                }
                break;
            case R.id.ambiete_giungla:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.giungla))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.giungla));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteGiungla);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.giungla));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteGiungla);
                }
                break;
            case R.id.ambiete_inferno:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.inferno))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.inferno));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteInferno);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.inferno));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteInferno);
                }
                break;
            case R.id.ambiete_lago:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.lago))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.lago));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteLago);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.lago));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteLago);
                }
                break;
            case R.id.ambiete_limbo:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.limbo))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.limbo));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteLimbo);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.limbo));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteLimbo);
                }
                break;
            case R.id.ambiete_montagna:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.montagna))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.montagna));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteMontagna);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.montagna));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteMontagna);
                }
                break;
            case R.id.ambiete_nomade:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.nomade))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.nomade));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteNomade);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.nomade));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteNomade);
                }
                break;
            case R.id.ambiete_ovunque:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.ovunque))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.ovunque));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteOvunque);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.ovunque));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteOvunque);
                }
                break;
            case R.id.ambiete_palude:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.palude))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.palude));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambietePalude);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.palude));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambietePalude);
                }
                break;
            case R.id.ambiete_paradiso:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.paradiso))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.paradiso));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteParadiso);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.paradiso));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteParadiso);
                }
                break;
            case R.id.ambiete_pianoDelFuoco:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.piano_del_fuoco))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.piano_del_fuoco));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambietePianoDelFuoco);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.piano_del_fuoco));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambietePianoDelFuoco);
                }
                break;
            case R.id.ambiete_pianoDellAcqua:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.piano_dell_acqua))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.piano_dell_acqua));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambietePianoDellAcqua);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.piano_dell_acqua));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambietePianoDellAcqua);
                }
                break;
            case R.id.ambiete_pianoDellAria:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.piano_dell_aria))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.piano_dell_aria));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambietePianoDellAria);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.piano_dell_aria));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambietePianoDellAria);
                }
                break;
            case R.id.ambiete_pianoDellaTerra:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.piano_della_terra))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.piano_della_terra));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambietePianoDellaTerra);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.piano_della_terra));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambietePianoDellaTerra);
                }
                break;
            case R.id.ambiete_pianura:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.pianura))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.pianura));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambietePianura);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.pianura));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambietePianura);
                }
                break;
            case R.id.ambiete_profonditàMarine:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.profondit_marine))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.profondit_marine));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteProfonditàMarine);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.profondit_marine));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteProfonditàMarine);
                }
                break;
            case R.id.ambiete_rovine:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.rovine))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.rovine));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteRovine);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.rovine));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteRovine);
                }
                break;
            case R.id.ambiete_servitore:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.servitore))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.servitore));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteServitore);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.servitore));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteServitore);
                }
                break;
            case R.id.ambiete_terreSelvaggie:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.terre_selvaggie))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.terre_selvaggie));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteTerreSelvaggie);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.terre_selvaggie));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteTerreSelvaggie);
                }
                break;
            case R.id.ambiete_underdark:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.underdark))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.underdark));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteUnderdark);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.underdark));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteUnderdark);
                }
                break;
            case R.id.ambiete_vulcano:
                if (SearchMonstersFragment.selectedAmbieteFilters.contains(getResources().getString(R.string.vulcano))) {
                    SearchMonstersFragment.selectedAmbieteFilters.remove(getResources().getString(R.string.vulcano));
                    if (SearchMonstersFragment.selectedAmbieteFilters.size() == 0) {
                        SearchMonstersFragment.isAmbienteSelected = false;
                    }
                    lookDeselected(ambieteVulcano);
                }else {
                    SearchMonstersFragment.selectedAmbieteFilters.add(getResources().getString(R.string.vulcano));
                    SearchMonstersFragment.isAmbienteSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(ambieteVulcano);
                }
                break;
            case R.id.categoria_aberrazione:
                if (SearchMonstersFragment.selectedCategoriaFilters.contains(getResources().getString(R.string.aberrazione))) {
                    SearchMonstersFragment.selectedCategoriaFilters.remove(getResources().getString(R.string.aberrazione));
                    if (SearchMonstersFragment.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersFragment.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaAberrazione);
                }else {
                    SearchMonstersFragment.selectedCategoriaFilters.add(getResources().getString(R.string.aberrazione));
                    SearchMonstersFragment.isCategoriaSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(categoriaAberrazione);
                }
                break;
            case R.id.categoria_bestia:
                if (SearchMonstersFragment.selectedCategoriaFilters.contains(getResources().getString(R.string.bestia))) {
                    SearchMonstersFragment.selectedCategoriaFilters.remove(getResources().getString(R.string.bestia));
                    if (SearchMonstersFragment.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersFragment.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaBestia);
                }else {
                    SearchMonstersFragment.selectedCategoriaFilters.add(getResources().getString(R.string.bestia));
                    SearchMonstersFragment.isCategoriaSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(categoriaBestia);
                }
                break;
            case R.id.categoria_celestiale:
                if (SearchMonstersFragment.selectedCategoriaFilters.contains(getResources().getString(R.string.celestiale))) {
                    SearchMonstersFragment.selectedCategoriaFilters.remove(getResources().getString(R.string.celestiale));
                    if (SearchMonstersFragment.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersFragment.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaCelestiale);
                }else {
                    SearchMonstersFragment.selectedCategoriaFilters.add(getResources().getString(R.string.celestiale));
                    SearchMonstersFragment.isCategoriaSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(categoriaCelestiale);
                }
                break;
            case R.id.categoria_costrutto:
                if (SearchMonstersFragment.selectedCategoriaFilters.contains(getResources().getString(R.string.costrutto))) {
                    SearchMonstersFragment.selectedCategoriaFilters.remove(getResources().getString(R.string.costrutto));
                    if (SearchMonstersFragment.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersFragment.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaCostrutto);
                }else {
                    SearchMonstersFragment.selectedCategoriaFilters.add(getResources().getString(R.string.costrutto));
                    SearchMonstersFragment.isCategoriaSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(categoriaCostrutto);
                }
                break;
            case R.id.categoria_drago:
                if (SearchMonstersFragment.selectedCategoriaFilters.contains(getResources().getString(R.string.drago))) {
                    SearchMonstersFragment.selectedCategoriaFilters.remove(getResources().getString(R.string.drago));
                    if (SearchMonstersFragment.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersFragment.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaDrago);
                }else {
                    SearchMonstersFragment.selectedCategoriaFilters.add(getResources().getString(R.string.drago));
                    SearchMonstersFragment.isCategoriaSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(categoriaDrago);
                }
                break;
            case R.id.categoria_elementale:
                if (SearchMonstersFragment.selectedCategoriaFilters.contains(getResources().getString(R.string.elementale))) {
                    SearchMonstersFragment.selectedCategoriaFilters.remove(getResources().getString(R.string.elementale));
                    if (SearchMonstersFragment.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersFragment.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaElementale);
                }else {
                    SearchMonstersFragment.selectedCategoriaFilters.add(getResources().getString(R.string.elementale));
                    SearchMonstersFragment.isCategoriaSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(categoriaElementale);
                }
                break;
            case R.id.categoria_folletto:
                if (SearchMonstersFragment.selectedCategoriaFilters.contains(getResources().getString(R.string.folletto))) {
                    SearchMonstersFragment.selectedCategoriaFilters.remove(getResources().getString(R.string.folletto));
                    if (SearchMonstersFragment.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersFragment.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaFolletto);
                }else {
                    SearchMonstersFragment.selectedCategoriaFilters.add(getResources().getString(R.string.folletto));
                    SearchMonstersFragment.isCategoriaSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(categoriaFolletto);
                }
                break;
            case R.id.categoria_gigante:
                if (SearchMonstersFragment.selectedCategoriaFilters.contains(getResources().getString(R.string.gigante))) {
                    SearchMonstersFragment.selectedCategoriaFilters.remove(getResources().getString(R.string.gigante));
                    if (SearchMonstersFragment.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersFragment.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaGigante);
                }else {
                    SearchMonstersFragment.selectedCategoriaFilters.add(getResources().getString(R.string.gigante));
                    SearchMonstersFragment.isCategoriaSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(categoriaGigante);
                }
                break;
            case R.id.categoria_immondo:
                if (SearchMonstersFragment.selectedCategoriaFilters.contains(getResources().getString(R.string.immondo))) {
                    SearchMonstersFragment.selectedCategoriaFilters.remove(getResources().getString(R.string.immondo));
                    if (SearchMonstersFragment.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersFragment.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaImmondo);
                }else {
                    SearchMonstersFragment.selectedCategoriaFilters.add(getResources().getString(R.string.immondo));
                    SearchMonstersFragment.isCategoriaSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(categoriaImmondo);
                }
                break;
            case R.id.categoria_melma:
                if (SearchMonstersFragment.selectedCategoriaFilters.contains(getResources().getString(R.string.melma))) {
                    SearchMonstersFragment.selectedCategoriaFilters.remove(getResources().getString(R.string.melma));
                    if (SearchMonstersFragment.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersFragment.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaMelma);
                }else {
                    SearchMonstersFragment.selectedCategoriaFilters.add(getResources().getString(R.string.melma));
                    SearchMonstersFragment.isCategoriaSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(categoriaMelma);
                }
                break;
            case R.id.categoria_mostruosità:
                if (SearchMonstersFragment.selectedCategoriaFilters.contains(getResources().getString(R.string.mostruosit))) {
                    SearchMonstersFragment.selectedCategoriaFilters.remove(getResources().getString(R.string.mostruosit));
                    if (SearchMonstersFragment.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersFragment.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaMostruosità);
                }else {
                    SearchMonstersFragment.selectedCategoriaFilters.add(getResources().getString(R.string.mostruosit));
                    SearchMonstersFragment.isCategoriaSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(categoriaMostruosità);
                }
                break;
            case R.id.categoria_nonMorto:
                if (SearchMonstersFragment.selectedCategoriaFilters.contains(getResources().getString(R.string.non_morto))) {
                    SearchMonstersFragment.selectedCategoriaFilters.remove(getResources().getString(R.string.non_morto));
                    if (SearchMonstersFragment.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersFragment.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaNonMorto);
                }else {
                    SearchMonstersFragment.selectedCategoriaFilters.add(getResources().getString(R.string.non_morto));
                    SearchMonstersFragment.isCategoriaSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(categoriaNonMorto);
                }
                break;
            case R.id.categoria_umanoide:
                if (SearchMonstersFragment.selectedCategoriaFilters.contains(getResources().getString(R.string.umanoide))) {
                    SearchMonstersFragment.selectedCategoriaFilters.remove(getResources().getString(R.string.umanoide));
                    if (SearchMonstersFragment.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersFragment.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaUmanoide);
                }else {
                    SearchMonstersFragment.selectedCategoriaFilters.add(getResources().getString(R.string.umanoide));
                    SearchMonstersFragment.isCategoriaSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(categoriaUmanoide);
                }
                break;
            case R.id.categoria_vegetale:
                if (SearchMonstersFragment.selectedCategoriaFilters.contains(getResources().getString(R.string.vegetale))) {
                    SearchMonstersFragment.selectedCategoriaFilters.remove(getResources().getString(R.string.vegetale));
                    if (SearchMonstersFragment.selectedCategoriaFilters.size() == 0) {
                        SearchMonstersFragment.isCategoriaSelected = false;
                    }
                    lookDeselected(categoriaVegetale);
                }else {
                    SearchMonstersFragment.selectedCategoriaFilters.add(getResources().getString(R.string.vegetale));
                    SearchMonstersFragment.isCategoriaSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(categoriaVegetale);
                }
                break;
            case R.id.taglia_enorme:
                if (SearchMonstersFragment.selectedTagliaFilters.contains(getResources().getString(R.string.enorme))) {
                    SearchMonstersFragment.selectedTagliaFilters.remove(getResources().getString(R.string.enorme));
                    if (SearchMonstersFragment.selectedTagliaFilters.size() == 0) {
                        SearchMonstersFragment.isTagliaSelected = false;
                    }
                    lookDeselected(tagliaEnorme);
                }else {
                    SearchMonstersFragment.selectedTagliaFilters.add(getResources().getString(R.string.enorme));
                    SearchMonstersFragment.isTagliaSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(tagliaEnorme);
                }
                break;
            case R.id.taglia_grande:
                if (SearchMonstersFragment.selectedTagliaFilters.contains(getResources().getString(R.string.grande))) {
                    SearchMonstersFragment.selectedTagliaFilters.remove(getResources().getString(R.string.grande));
                    if (SearchMonstersFragment.selectedTagliaFilters.size() == 0) {
                        SearchMonstersFragment.isTagliaSelected = false;
                    }
                    lookDeselected(tagliaGrande);
                }else {
                    SearchMonstersFragment.selectedTagliaFilters.add(getResources().getString(R.string.grande));
                    SearchMonstersFragment.isTagliaSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(tagliaGrande);
                }
                break;
            case R.id.taglia_mastodontica:
                if (SearchMonstersFragment.selectedTagliaFilters.contains(getResources().getString(R.string.mastodontica))) {
                    SearchMonstersFragment.selectedTagliaFilters.remove(getResources().getString(R.string.mastodontica));
                    if (SearchMonstersFragment.selectedTagliaFilters.size() == 0) {
                        SearchMonstersFragment.isTagliaSelected = false;
                    }
                    lookDeselected(tagliaMastodontica);
                }else {
                    SearchMonstersFragment.selectedTagliaFilters.add(getResources().getString(R.string.mastodontica));
                    SearchMonstersFragment.isTagliaSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(tagliaMastodontica);
                }
                break;
            case R.id.taglia_media:
                if (SearchMonstersFragment.selectedTagliaFilters.contains(getResources().getString(R.string.media))) {
                    SearchMonstersFragment.selectedTagliaFilters.remove(getResources().getString(R.string.media));
                    if (SearchMonstersFragment.selectedTagliaFilters.size() == 0) {
                        SearchMonstersFragment.isTagliaSelected = false;
                    }
                    lookDeselected(tagliaMedia);
                }else {
                    SearchMonstersFragment.selectedTagliaFilters.add(getResources().getString(R.string.media));
                    SearchMonstersFragment.isTagliaSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(tagliaMedia);
                }
                break;
            case R.id.taglia_minuscola:
                if (SearchMonstersFragment.selectedTagliaFilters.contains(getResources().getString(R.string.minuscola))) {
                    SearchMonstersFragment.selectedTagliaFilters.remove(getResources().getString(R.string.minuscola));
                    if (SearchMonstersFragment.selectedTagliaFilters.size() == 0) {
                        SearchMonstersFragment.isTagliaSelected = false;
                    }
                    lookDeselected(tagliaMinuscola);
                }else {
                    SearchMonstersFragment.selectedTagliaFilters.add(getResources().getString(R.string.minuscola));
                    SearchMonstersFragment.isTagliaSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(tagliaMinuscola);
                }
                break;
            case R.id.taglia_piccola:
                if (SearchMonstersFragment.selectedTagliaFilters.contains(getResources().getString(R.string.piccola))) {
                    SearchMonstersFragment.selectedTagliaFilters.remove(getResources().getString(R.string.piccola));
                    if (SearchMonstersFragment.selectedTagliaFilters.size() == 0) {
                        SearchMonstersFragment.isTagliaSelected = false;
                    }
                    lookDeselected(tagliaPiccola);
                }else {
                    SearchMonstersFragment.selectedTagliaFilters.add(getResources().getString(R.string.piccola));
                    SearchMonstersFragment.isTagliaSelected = true;
                    //SearchMonstersFragment.isFiltersApplied = true;
                    lookSelected(tagliaPiccola);
                }
                break;
            case R.id.id_clear_btn:
                SearchMonstersFragment.isFiltersApplied = true;
                SearchMonstersFragment activity = (SearchMonstersFragment) getParentFragment();
                if (activity != null)
                    activity.setFilters();
                break;
        }
    }

    private void initColors()
    {
        white = ContextCompat.getColor(requireContext().getApplicationContext(), R.color.white);
        rossoPorpora = ContextCompat.getColor(requireContext().getApplicationContext(), R.color.rossoPorpora);
        cream = ContextCompat.getColor(requireContext().getApplicationContext(), R.color.creamColor);
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
