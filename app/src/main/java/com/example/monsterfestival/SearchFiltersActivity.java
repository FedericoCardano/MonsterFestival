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
    LinearLayout raceDwarf, raceElf, raceHalfling, raceHuman, raceDragonborn, raceGnome, raceHalfElf, raceHalfOrc, raceTiefling;
    LinearLayout m_classBarbarian, m_classBard, m_classCleric, m_classDruid, m_classFighter, m_classMonk, m_classPaladin, m_classRanger, m_classRogue, m_classSorcerer, m_classWarlock, m_classWizard;
    LinearLayout backgroundAcolyte, backgroundCharlatan, backgroundCriminal, backgroundEntertainer, backgroundFolkHero, backgroundGuildArtisan, backgroundHermit, backgroundNoble, backgroundOutlander, backgroundSage, backgroundSailor, backgroundSoldier, backgroundUrchin;
    LinearLayout alignmentLawfulGood, alignmentNeutralGood, alignmentChaoticGood, alignmentLawfulNeutral, alignmentNeutral, alignmentChaoticNeutral, alignmentLawfulEvil, alignmentNeutralEvil, alignmentChaoticEvil;

    private int white, rossoPorpora, cream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filters);

        initColors();

        initAll();

        clearFilters.setOnClickListener(this);

        raceDwarf.setOnClickListener(this);
        raceElf.setOnClickListener(this);
        raceHalfling.setOnClickListener(this);
        raceHuman.setOnClickListener(this);
        raceDragonborn.setOnClickListener(this);
        raceGnome.setOnClickListener(this);
        raceHalfElf.setOnClickListener(this);
        raceHalfOrc.setOnClickListener(this);
        raceTiefling.setOnClickListener(this);

        m_classBarbarian.setOnClickListener(this);
        m_classBard.setOnClickListener(this);
        m_classCleric.setOnClickListener(this);
        m_classDruid.setOnClickListener(this);
        m_classFighter.setOnClickListener(this);
        m_classMonk.setOnClickListener(this);
        m_classPaladin.setOnClickListener(this);
        m_classRanger.setOnClickListener(this);
        m_classRogue.setOnClickListener(this);
        m_classSorcerer.setOnClickListener(this);
        m_classWarlock.setOnClickListener(this);
        m_classWizard.setOnClickListener(this);

        backgroundAcolyte.setOnClickListener(this);
        backgroundCharlatan.setOnClickListener(this);
        backgroundCriminal.setOnClickListener(this);
        backgroundEntertainer.setOnClickListener(this);
        backgroundFolkHero.setOnClickListener(this);
        backgroundGuildArtisan.setOnClickListener(this);
        backgroundHermit.setOnClickListener(this);
        backgroundNoble.setOnClickListener(this);
        backgroundOutlander.setOnClickListener(this);
        backgroundSage.setOnClickListener(this);
        backgroundSailor.setOnClickListener(this);
        backgroundSoldier.setOnClickListener(this);
        backgroundUrchin.setOnClickListener(this);

        alignmentLawfulGood.setOnClickListener(this);
        alignmentNeutralGood.setOnClickListener(this);
        alignmentChaoticGood.setOnClickListener(this);
        alignmentLawfulNeutral.setOnClickListener(this);
        alignmentNeutral.setOnClickListener(this);
        alignmentChaoticNeutral.setOnClickListener(this);
        alignmentLawfulEvil.setOnClickListener(this);
        alignmentNeutralEvil.setOnClickListener(this);
        alignmentChaoticEvil.setOnClickListener(this);
    }

    private void initAll() {
        clearFilters = findViewById(R.id.id_clear_btn);
        raceDwarf = findViewById(R.id.race_Dwarf);
        raceElf = findViewById(R.id.race_Elf);
        raceHalfling = findViewById(R.id.race_Halfling);
        raceHuman = findViewById(R.id.race_Human);
        raceDragonborn = findViewById(R.id.race_Dragonborn);
        raceGnome = findViewById(R.id.race_Gnome);
        raceHalfElf = findViewById(R.id.race_HalfElf);
        raceHalfOrc = findViewById(R.id.race_HalfOrc);
        raceTiefling = findViewById(R.id.race_Tiefling);

        m_classBarbarian = findViewById(R.id.class_Barbarian);
        m_classBard = findViewById(R.id.class_Bard);
        m_classCleric = findViewById(R.id.class_Cleric);
        m_classDruid = findViewById(R.id.class_Druid);
        m_classFighter = findViewById(R.id.class_Fighter);
        m_classMonk = findViewById(R.id.class_Monk);
        m_classPaladin = findViewById(R.id.class_Paladin);
        m_classRanger = findViewById(R.id.class_Ranger);
        m_classRogue = findViewById(R.id.class_Rogue);
        m_classSorcerer = findViewById(R.id.class_Sorcerer);
        m_classWarlock = findViewById(R.id.class_Warlock);
        m_classWizard = findViewById(R.id.class_Wizard);

        backgroundAcolyte = findViewById(R.id.back_Acolyte);
        backgroundCharlatan = findViewById(R.id.back_Charlatan);
        backgroundCriminal = findViewById(R.id.back_Criminal);
        backgroundEntertainer = findViewById(R.id.back_Entertainer);
        backgroundFolkHero = findViewById(R.id.back_FolkHero);
        backgroundGuildArtisan = findViewById(R.id.back_GuildArtisan);
        backgroundHermit = findViewById(R.id.back_Hermit);
        backgroundNoble = findViewById(R.id.back_Noble);
        backgroundOutlander = findViewById(R.id.back_Outlander);
        backgroundSage = findViewById(R.id.back_Sage);
        backgroundSailor = findViewById(R.id.back_Sailor);
        backgroundSoldier = findViewById(R.id.back_Soldier);
        backgroundUrchin = findViewById(R.id.back_Urchin);

        alignmentLawfulGood = findViewById(R.id.ali_LawfulGood);
        alignmentNeutralGood = findViewById(R.id.ali_NeutralGood);
        alignmentChaoticGood = findViewById(R.id.ali_ChaoticGood);
        alignmentLawfulNeutral = findViewById(R.id.ali_LawfulNeutral);
        alignmentNeutral = findViewById(R.id.ali_Neutral);
        alignmentChaoticNeutral = findViewById(R.id.ali_ChaoticNeutral);
        alignmentLawfulEvil = findViewById(R.id.ali_LawfulEvil);
        alignmentNeutralEvil = findViewById(R.id.ali_NeutralEvil);
        alignmentChaoticEvil = findViewById(R.id.ali_ChaoticEvil);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.race_Dwarf:
                SearchMonstersActivity.selectedRaceFilters.add("Dwarf");
                SearchMonstersActivity.isRaceSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(raceDwarf);
                //finish();
                break;
            case R.id.race_Elf:
                SearchMonstersActivity.selectedRaceFilters.add("Elf");
                SearchMonstersActivity.isRaceSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(raceElf);
                //finish();
                break;
            case R.id.race_Halfling:
                SearchMonstersActivity.selectedRaceFilters.add("Halfling");
                SearchMonstersActivity.isRaceSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(raceHalfling);
                //finish();
                break;
            case R.id.race_Human:
                SearchMonstersActivity.selectedRaceFilters.add("Human");
                SearchMonstersActivity.isRaceSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(raceHuman);
                //finish();
                break;
            case R.id.race_Dragonborn:
                SearchMonstersActivity.selectedRaceFilters.add("Dragonborn");
                SearchMonstersActivity.isRaceSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(raceDragonborn);
                //finish();
                break;
            case R.id.race_Gnome:
                SearchMonstersActivity.selectedRaceFilters.add("Gnome");
                SearchMonstersActivity.isRaceSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(raceGnome);
                //finish();
                break;
            case R.id.race_HalfElf:
                SearchMonstersActivity.selectedRaceFilters.add("Half-Elf");
                SearchMonstersActivity.isRaceSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(raceHalfElf);
                //finish();
                break;
            case R.id.race_HalfOrc:
                SearchMonstersActivity.selectedRaceFilters.add("Half-Orc");
                SearchMonstersActivity.isRaceSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(raceHalfOrc);
                //finish();
                break;
            case R.id.race_Tiefling:
                SearchMonstersActivity.selectedRaceFilters.add("Tiefling");
                SearchMonstersActivity.isRaceSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(raceTiefling);
                //finish();
                break;
            case R.id.class_Barbarian:
                SearchMonstersActivity.selectedM_ClassFilters.add("Barbarian");
                SearchMonstersActivity.isM_ClassSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(m_classBarbarian);
                //finish();
                break;
            case R.id.class_Bard:
                SearchMonstersActivity.selectedM_ClassFilters.add("Bard");
                SearchMonstersActivity.isM_ClassSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(m_classBard);
                //finish();
                break;
            case R.id.class_Cleric:
                SearchMonstersActivity.selectedM_ClassFilters.add("Cleric");
                SearchMonstersActivity.isM_ClassSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(m_classCleric);
                //finish();
                break;
            case R.id.class_Druid:
                SearchMonstersActivity.selectedM_ClassFilters.add("Druid");
                SearchMonstersActivity.isM_ClassSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(m_classDruid);
                //finish();
                break;
            case R.id.class_Fighter:
                SearchMonstersActivity.selectedM_ClassFilters.add("Fighter");
                SearchMonstersActivity.isM_ClassSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(m_classFighter);
                //finish();
                break;
            case R.id.class_Monk:
                SearchMonstersActivity.selectedM_ClassFilters.add("Monk");
                SearchMonstersActivity.isM_ClassSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(m_classMonk);
                //finish();
                break;
            case R.id.class_Paladin:
                SearchMonstersActivity.selectedM_ClassFilters.add("Paladin");
                SearchMonstersActivity.isM_ClassSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(m_classPaladin);
                //finish();
                break;
            case R.id.class_Ranger:
                SearchMonstersActivity.selectedM_ClassFilters.add("Ranger");
                SearchMonstersActivity.isM_ClassSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(m_classRanger);
                //finish();
                break;
            case R.id.class_Rogue:
                SearchMonstersActivity.selectedM_ClassFilters.add("Rogue");
                SearchMonstersActivity.isM_ClassSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(m_classRogue);
                //finish();
                break;
            case R.id.class_Sorcerer:
                SearchMonstersActivity.selectedM_ClassFilters.add("Sorcerer");
                SearchMonstersActivity.isM_ClassSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(m_classSorcerer);
                //finish();
                break;
            case R.id.class_Warlock:
                SearchMonstersActivity.selectedM_ClassFilters.add("Warlock");
                SearchMonstersActivity.isM_ClassSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(m_classWarlock);
                //finish();
                break;
            case R.id.class_Wizard:
                SearchMonstersActivity.selectedM_ClassFilters.add("Wizard");
                SearchMonstersActivity.isM_ClassSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(m_classWizard);
                //finish();
                break;
            case R.id.back_Acolyte:
                SearchMonstersActivity.selectedBackgroundFilters.add("Acolyte");
                SearchMonstersActivity.isBackgroundSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(backgroundAcolyte);
                //finish();
                break;
            case R.id.back_Charlatan:
                SearchMonstersActivity.selectedBackgroundFilters.add("Charlatan");
                SearchMonstersActivity.isBackgroundSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(backgroundCharlatan);
                //finish();
                break;
            case R.id.back_Criminal:
                SearchMonstersActivity.selectedBackgroundFilters.add("Criminal");
                SearchMonstersActivity.isBackgroundSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(backgroundCriminal);
                //finish();
                break;
            case R.id.back_Entertainer:
                SearchMonstersActivity.selectedBackgroundFilters.add("Entertainer");
                SearchMonstersActivity.isBackgroundSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(backgroundEntertainer);
                //finish();
                break;
            case R.id.back_FolkHero:
                SearchMonstersActivity.selectedBackgroundFilters.add("Folk Hero");
                SearchMonstersActivity.isBackgroundSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(backgroundFolkHero);
                //finish();
                break;
            case R.id.back_GuildArtisan:
                SearchMonstersActivity.selectedBackgroundFilters.add("Guild Artisan");
                SearchMonstersActivity.isBackgroundSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(backgroundGuildArtisan);
                //finish();
                break;
            case R.id.back_Hermit:
                SearchMonstersActivity.selectedBackgroundFilters.add("Hermit");
                SearchMonstersActivity.isBackgroundSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(backgroundHermit);
                //finish();
                break;
            case R.id.back_Noble:
                SearchMonstersActivity.selectedBackgroundFilters.add("Noble");
                SearchMonstersActivity.isBackgroundSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(backgroundNoble);
                //finish();
                break;
            case R.id.back_Outlander:
                SearchMonstersActivity.selectedBackgroundFilters.add("Outlander");
                SearchMonstersActivity.isBackgroundSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(backgroundOutlander);
                //finish();
                break;
            case R.id.back_Sage:
                SearchMonstersActivity.selectedBackgroundFilters.add("Sage");
                SearchMonstersActivity.isBackgroundSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(backgroundSage);
                //finish();
                break;
            case R.id.back_Sailor:
                SearchMonstersActivity.selectedBackgroundFilters.add("Sailor");
                SearchMonstersActivity.isBackgroundSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(backgroundSailor);
                //finish();
                break;
            case R.id.back_Soldier:
                SearchMonstersActivity.selectedBackgroundFilters.add("Soldier");
                SearchMonstersActivity.isBackgroundSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(backgroundSoldier);
                //finish();
                break;
            case R.id.back_Urchin:
                SearchMonstersActivity.selectedBackgroundFilters.add("Urchin");
                SearchMonstersActivity.isBackgroundSelected = true;
                SearchMonstersActivity.isFiltersApplied = true;
                lookSelected(backgroundUrchin);
                //finish();
                break;
            case R.id.ali_LawfulGood:
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
                break;
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