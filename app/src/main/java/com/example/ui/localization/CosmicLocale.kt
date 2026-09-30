package com.example.ui.localization

enum class AppLanguage(val code: String, val label: String, val flagOrIcon: String) {
    ENGLISH("en", "English", "EN"),
    BENGALI("bn", "বাংলা", "BN")
}

object CosmicStrings {
    fun appTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "CosmoSphere 3D"
        AppLanguage.BENGALI -> "কসমোস্ফিয়ার ৩ডি"
    }

    fun appSubtitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "3D Solar System & Interactive Stellar Map"
        AppLanguage.BENGALI -> "সৌরজগত ও মহাজাগতিক ৩ডি মানচিত্র"
    }

    fun fullscreenView(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "360° Fullscreen View"
        AppLanguage.BENGALI -> "ফুল স্ক্রিন মোড (৩৬০°)"
    }

    fun enterFullscreenButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Full Screen Mode"
        AppLanguage.BENGALI -> "ফুল স্ক্রিন মোড"
    }

    fun exitFullscreen(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Exit Fullscreen"
        AppLanguage.BENGALI -> "ফুলস্ক্রিন বন্ধ করুন"
    }

    fun orbitSpeedLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Orbit Speed:"
        AppLanguage.BENGALI -> "কক্ষপথ গতি:"
    }

    fun speedPause(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Pause"
        AppLanguage.BENGALI -> "বিরতি"
    }

    fun speedCalm(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "0.2x Calm"
        AppLanguage.BENGALI -> "০.২x শান্ত"
    }

    fun speedNormal(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "0.5x Serene"
        AppLanguage.BENGALI -> "০.৫x স্বাভাবিক"
    }

    fun speedFast(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "1x Fast"
        AppLanguage.BENGALI -> "১x দ্রুত"
    }

    fun speedHyper(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "2x Warp"
        AppLanguage.BENGALI -> "২x হাইপার"
    }

    fun focusUnlocked(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Focus Unlocked"
        AppLanguage.BENGALI -> "ফোকাস আনলক"
    }

    fun focusedOn(name: String, lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Focused: $name"
        AppLanguage.BENGALI -> "ফোকাস: $name"
    }

    fun inspectPlanetButton(name: String, lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Inspect $name in 3D & Time"
        AppLanguage.BENGALI -> "$name ৩ডি ও সময় বিশদ পরিদর্শন"
    }

    fun yearTimeTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Orbit & Planetary Time"
        AppLanguage.BENGALI -> "কক্ষপথ ও গ্রহীয় সময় চক্র"
    }

    fun yearLengthLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "1 Year (Orbit Period)"
        AppLanguage.BENGALI -> "১ বছর (সূর্য প্রদক্ষিণ)"
    }

    fun dayLengthLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "1 Day (Axial Rotation)"
        AppLanguage.BENGALI -> "১ দিন (নিজের অক্ষে ঘূর্ণন)"
    }

    fun ageCalculatorTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Your Planetary Age & Time Converter"
        AppLanguage.BENGALI -> "আপনার বয়স ও মহাজাগতিক সময় রূপান্তরকারী"
    }

    fun earthAgeLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Your Age on Earth (Years)"
        AppLanguage.BENGALI -> "পৃথিবীতে আপনার বয়স (বছর)"
    }

    fun ageOnWorldLabel(name: String, lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Your Age on $name:"
        AppLanguage.BENGALI -> "$name-এ আপনার বয়স:"
    }

    fun birthdaysCelebrated(count: String, lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "$count Birthdays celebrated!"
        AppLanguage.BENGALI -> "$count টি জন্মদিন উদযাপিত হয়েছে!"
    }

    fun sunrisesWitnessed(count: String, lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Approx. $count Sunrises witnessed"
        AppLanguage.BENGALI -> "প্রায় $count টি সূর্যোদয় প্রত্যক্ষ করেছেন"
    }

    fun navSolar(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Solar 3D"
        AppLanguage.BENGALI -> "সৌরজগৎ ৩ডি"
    }

    fun navPlanet(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Planet 3D"
        AppLanguage.BENGALI -> "গ্রহ পরিদর্শন"
    }

    fun navStellar(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Stellar Map"
        AppLanguage.BENGALI -> "স্টেলার ম্যাপ"
    }

    fun navJournal(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Cosmic Log"
        AppLanguage.BENGALI -> "মহাকাশ ডায়রি"
    }

    fun settingsTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Language & Settings"
        AppLanguage.BENGALI -> "ভাষা ও সেটিংস"
    }

    fun selectLanguage(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Select Language / ভাষা নির্বাচন করুন:"
        AppLanguage.BENGALI -> "ভাষা নির্বাচন করুন / Select Language:"
    }

    fun fullScreenHint(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Drag anywhere to rotate 360° • Tap any Planet or Galaxy for deep details"
        AppLanguage.BENGALI -> "৩৬০° যেকোনো কোণে ঘুরিয়ে দেখুন • যেকোনো গ্রহ বা গ্যালাক্সিতে ট্যাপ করে বিস্তারিত জানুন"
    }

    fun specDiameter(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Diameter"
        AppLanguage.BENGALI -> "ব্যাস"
    }

    fun specMass(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Mass"
        AppLanguage.BENGALI -> "ভর"
    }

    fun specGravity(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Surface Gravity"
        AppLanguage.BENGALI -> "পৃষ্ঠ মাধ্যাকর্ষণ"
    }

    fun specDistSun(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Distance from Sun"
        AppLanguage.BENGALI -> "সূর্য থেকে দূরত্ব"
    }

    fun specMoons(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Moons"
        AppLanguage.BENGALI -> "উপগ্রহের সংখ্যা"
    }

    fun specTemp(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Avg Temperature"
        AppLanguage.BENGALI -> "গড় তাপমাত্রা"
    }
}
