package com.example.data.datasource

import androidx.compose.ui.graphics.Color
import com.example.ui.localization.AppLanguage

data class CosmicEpoch(
    val id: String,
    val yearLabelEn: String,
    val yearLabelBn: String,
    val titleEn: String,
    val titleBn: String,
    val eraEn: String,
    val eraBn: String,
    val descriptionEn: String,
    val descriptionBn: String,
    val planetaryImpactEn: String,
    val planetaryImpactBn: String,
    val timeSliderRatio: Float, // 0.0 to 1.0
    val accentColor: Color,
    val speedMultiplier: Float = 0.5f
) {
    fun getYear(lang: AppLanguage): String = if (lang == AppLanguage.ENGLISH) yearLabelEn else yearLabelBn
    fun getTitle(lang: AppLanguage): String = if (lang == AppLanguage.ENGLISH) titleEn else titleBn
    fun getEra(lang: AppLanguage): String = if (lang == AppLanguage.ENGLISH) eraEn else eraBn
    fun getDescription(lang: AppLanguage): String = if (lang == AppLanguage.ENGLISH) descriptionEn else descriptionBn
    fun getImpact(lang: AppLanguage): String = if (lang == AppLanguage.ENGLISH) planetaryImpactEn else planetaryImpactBn
}

object TimeWarpData {

    val epochs: List<CosmicEpoch> = listOf(
        CosmicEpoch(
            id = "solar_birth",
            yearLabelEn = "-4.6 Billion Years",
            yearLabelBn = "-৪.৬ বিলিয়ন বছর আগে",
            titleEn = "Birth of Solar System & Accretion Disk",
            titleBn = "সৌরজগতের জন্ম ও প্রোটোপ্ল্যানেটারি ডিস্ক",
            eraEn = "Primordial Stellar Nursery",
            eraBn = "আদিম নীহারিকা যুগ",
            descriptionEn = "A massive molecular gas cloud collapsed under its own gravity, igniting nuclear fusion in our nascent Sun. Surrounding rings of glowing dust and molten rocks slowly clumped into the 8 planets.",
            descriptionBn = "একটি বিশাল হাইড্রোজেন মেঘ মহাকর্ষে সংকুচিত হয়ে আমাদের সূর্যের জন্ম দেয়। চারপাশের ঘূর্ণায়মান জ্বলন্ত ধূলিবলয় ও উল্কাপিণ্ডের সংঘর্ষে গড়ে ওঠে ৮টি নবীন গ্রহ।",
            planetaryImpactEn = "Planets undergo violent collisions (Late Heavy Bombardment); Earth collides with Theia to form the Moon.",
            planetaryImpactBn = "মহাপ্রলয়ঙ্করী উল্কাপাত ও থিয়ার সাথে পৃথিবীর সংঘর্ষে চাঁদের জন্ম হয়।",
            timeSliderRatio = 0.0f,
            accentColor = Color(0xFFFF6D00),
            speedMultiplier = 1.2f
        ),
        CosmicEpoch(
            id = "dinosaur_extinction",
            yearLabelEn = "-66 Million Years",
            yearLabelBn = "-৬.৬ কোটি বছর আগে",
            titleEn = "Chicxulub Asteroid Impact & Mass Extinction",
            titleBn = "চিকসুলুব উল্কাপাত ও ডাইনোসর বিলুপ্তি",
            eraEn = "Mesozoic Planetary Cataclysm",
            eraBn = "মেসোজোয়িক গ্রহীয় মহাপ্রলয়",
            descriptionEn = "A 10-kilometer-wide asteroid slammed into Earth at the Yucatán peninsula, unleashing energy equivalent to 10 billion atomic bombs and wiping out 75% of all species, clearing the path for mammals.",
            descriptionBn = "১০ কিমি প্রশস্ত এক দানবীয় গ্রহাণু পৃথিবীতে ঘণ্টায় ৭০,০০০ কিমি বেগে আঘাত হেনে ডাইনোসরসহ ৭৫% প্রজাতির বিলুপ্তি ঘটায় এবং স্তন্যপায়ী প্রাণীর বিকাশের পথ খুলে দেয়।",
            planetaryImpactEn = "Global megatsunamis, wildfire storms, and years of sulfuric winter darkness blanketing Earth.",
            planetaryImpactBn = "মহাসাগরে কিলোমিটার-উঁচু সুনামি ও ধূলিঝড়ে বছরের পর বছর সূর্য ঢেকে অন্ধকার শীত নেমে আসে।",
            timeSliderRatio = 0.20f,
            accentColor = Color(0xFFFF5252),
            speedMultiplier = 0.5f
        ),
        CosmicEpoch(
            id = "voyager_launch",
            yearLabelEn = "1977 CE",
            yearLabelBn = "১৯৭৭ খ্রিস্টাব্দ",
            titleEn = "Voyager 1 & 2 Humanity's Interstellar Messengers",
            titleBn = "ভয়েজার ১ ও ২: মহাশূন্যে মানুষের চিরন্তন পদচিহ্ন",
            eraEn = "Dawn of Space Exploration",
            eraBn = "মহাকাশ বিজয়ের সূচনা",
            descriptionEn = "NASA launches Voyager 1 and 2, embarking on the historic Grand Tour of Jupiter, Saturn, Uranus, and Neptune, carrying the Golden Record with greetings from Earth.",
            descriptionBn = "নাসা ভয়েজার ১ ও ২ উৎক্ষেপণ করে। সৌরমণ্ডলের বৃহস্পতি, শনি, ইউরেনাস ও নেপচুন পেরিয়ে মানুষের চিরন্তন বাণী 'গোল্ডেন রেকর্ড' নিয়ে তারা অনন্ত মহাশূন্যের উদ্দেশ্যে যাত্রা শুরু করে।",
            planetaryImpactEn = "Humanity captures the iconic 'Pale Blue Dot' portrait of Earth from 6 billion km away.",
            planetaryImpactBn = "৬০০ কোটি কিমি দূর থেকে তোলা হয় বিখ্যাত 'পেল ব্লু ডট' বা ফ্যাকাশে নীল বিন্দুরূপে পৃথিবীর রূপ।",
            timeSliderRatio = 0.40f,
            accentColor = Color(0xFF00E5FF),
            speedMultiplier = 0.2f
        ),
        CosmicEpoch(
            id = "present_day",
            yearLabelEn = "2026 CE (Today)",
            yearLabelBn = "২০২৬ খ্রিস্টাব্দ (বর্তমান)",
            titleEn = "Present Day: The Golden Age of Astronomy",
            titleBn = "বর্তমান মহাজাগতিক যুগ ও জেমস ওয়েব পর্যবেক্ষণ",
            eraEn = "Modern Anthropocene",
            eraBn = "আধুনিক প্রযুক্তি যুগ",
            descriptionEn = "Humanity observes the deepest cosmic web with the James Webb Space Telescope, while rovers search for past life on Mars, and Voyager explores interstellar space beyond the solar wind.",
            descriptionBn = "জেমস ওয়েব স্পেস টেলিস্কোপ দূরতম গ্যালাক্সির জন্ম দেখছে, মঙ্গল গ্রহে পারসিভিয়ারেন্স রোভার প্রাণের সন্ধান করছে এবং মানবজাতি মহাজাগতিক বিজ্ঞানের স্বর্ণযুগে পদার্পণ করেছে।",
            planetaryImpactEn = "Our 8 planets glide in perfect orbital balance; Earth teems with intelligent civilization looking upward.",
            planetaryImpactBn = "৮টি গ্রহ তাদের নিখুঁত কক্ষপথে সুষম গতিতে প্রদক্ষিণ করছে এবং মানুষ মহাশূন্যের রহস্য উন্মোচনে সচেষ্ট।",
            timeSliderRatio = 0.50f,
            accentColor = Color(0xFF64FFDA),
            speedMultiplier = 0.0f
        ),
        CosmicEpoch(
            id = "halley_2061",
            yearLabelEn = "2061 CE",
            yearLabelBn = "২০৬১ খ্রিস্টাব্দ",
            titleEn = "Return of Halley's Comet (1P/Halley)",
            titleBn = "হ্যালির ধূমকেতুর মহাজাগতিক প্রত্যাবর্তন",
            eraEn = "Near Future Celestial Spectacle",
            eraBn = "নিকট ভবিষ্যৎ মহাকাশ উৎসব",
            descriptionEn = "Halley's Comet reaches its perihelion, swooping inside Venus's orbit. Its icy coma will illuminate night skies around the globe with a luminous glowing tail millions of kilometers long.",
            descriptionBn = "৭৫ বছর পর আবার সূর্যের কাছে ফিরে আসবে বিখ্যাত হ্যালির ধূমকেতু। পৃথিবীর রাতের আকাশে কোটি কিলোমিটার দীর্ঘ জ্বলন্ত বরফ ও গ্যাসের উজ্জ্বল লেজ ছড়িয়ে এটি কোটি মানুষের চোখে ধরা দেবে।",
            planetaryImpactEn = "Astronomers around Earth will deploy robotic probes for direct sampling of cometary organic dust.",
            planetaryImpactBn = "বিশ্বব্যাপী মহাকাশযান পাঠিয়ে ধূমকেতুর ধূলিকণা সংগ্রহ ও বিশ্লেষণ করা হবে।",
            timeSliderRatio = 0.60f,
            accentColor = Color(0xFF82B1FF),
            speedMultiplier = 0.4f
        ),
        CosmicEpoch(
            id = "milkdromeda_collision",
            yearLabelEn = "+4.5 Billion Years",
            yearLabelBn = "+৪.৫ বিলিয়ন বছর পর",
            titleEn = "Milkdromeda: Milky Way & Andromeda Collision",
            titleBn = "মিল্কড্রোমিডা: মিল্কিওয়ে ও অ্যান্ড্রোমিডার মহামিলন",
            eraEn = "Galactic Fusion Era",
            eraBn = "গ্যালাকটিক মহামিলন যুগ",
            descriptionEn = "Andromeda Galaxy collides with our Milky Way at 110 km/s. Over hundreds of millions of years, their spiral structures interweave to form a colossal super-elliptical galaxy dubbed 'Milkdromeda'.",
            descriptionBn = "প্রতি সেকেন্ডে ১১০ কিমি গতিতে ধেয়ে আসা অ্যান্ড্রোমিডা গ্যালাক্সি আমাদের মিল্কিওয়ের সাথে মুখোমুখি সংঘর্ষ ঘটাবে। কোটি কোটি নক্ষত্র নতুন নৃত্যকক্ষে আবদ্ধ হয়ে জন্ম দেবে অতিকায় নতুন গ্যালাক্সি 'মিল্কড্রোমিডা'!",
            planetaryImpactEn = "Stars rarely collide due to vast space, but the sky will blaze with a radiant halo of a trillion intermingled suns.",
            planetaryImpactBn = "নক্ষত্রের মধ্যকার বিশাল শূন্যতার কারণে তারায় তারায় সংঘর্ষ না হলেও রাতের আকাশে কোটি কোটি জ্বলন্ত নক্ষত্রের মহামিলন দেখা যাবে।",
            timeSliderRatio = 0.80f,
            accentColor = Color(0xFFE040FB),
            speedMultiplier = 0.8f
        ),
        CosmicEpoch(
            id = "red_giant_sun",
            yearLabelEn = "+5 Billion Years",
            yearLabelBn = "+৫ বিলিয়ন বছর পর",
            titleEn = "Sun Becomes a Red Giant & Swallows Inner Worlds",
            titleBn = "সূর্য হবে রক্তিম দানব: ভেতরের গ্রহদের গ্রাস",
            eraEn = "Solar System Twilight",
            eraBn = "সৌরমণ্ডলের অস্তমিত অধ্যায়",
            descriptionEn = "Exhausting core hydrogen, the Sun expands outward over 200 times its current diameter. Mercury and Venus are vaporized; Earth's surface melts into a magma desert before being engulfed.",
            descriptionBn = "কেন্দ্রের হাইড্রোজেন শেষ হয়ে সূর্য প্রসারিত হয়ে বর্তমানের চেয়ে ২০০ গুণ বড় রক্তিম দানবে পরিণত হবে। বুধ ও শুক্র সম্পূর্ণ গিলে ফেলবে এবং গলিত লাভার মরুভূমিতে পরিণত হয়ে পৃথিবী বিলীন হয়ে যাবে।",
            planetaryImpactEn = "Outer icy moons like Europa and Titan thaw briefly into temperate ocean worlds before the Sun collapses into a white dwarf.",
            planetaryImpactBn = "শনি ও বৃহস্পতির বরফময় চাঁদগুলো (টাইটান ও ইউরোপা) ক্ষণিকের জন্য উষ্ণ মহাসাগরে রূপ নেবে।",
            timeSliderRatio = 0.90f,
            accentColor = Color(0xFFFF1744),
            speedMultiplier = 0.6f
        ),
        CosmicEpoch(
            id = "heat_death",
            yearLabelEn = "+100 Trillion Years",
            yearLabelBn = "+১০০ ট্রিলিয়ন বছর পর",
            titleEn = "The Degenerate Era & Final Cosmic Stillness",
            titleBn = "মহাবিশ্বের শেষ তারা ও পরম শীতল নীরবতা",
            eraEn = "Cosmic Heat Death",
            eraBn = "মহাজাগতিক তাপীয় অবলুপ্তি",
            descriptionEn = "All nuclear fuel in stars is permanently spent. Red dwarfs cool into black dwarfs; only black holes slowly evaporate over googols of years via Hawking Radiation until eternal cold peace reigns.",
            descriptionBn = "মহাবিশ্বের সকল নক্ষত্রের পারমাণবিক জ্বালানি চিরতরে ফুরিয়ে যাবে। কেবল কৃষ্ণগহ্বরগুলো হকিং বিকিরণের মাধ্যমে কোটি কোটি বছর ধরে বিলীন হবে—নেমে আসবে পরম শূন্যতার মহাজাগতিক শান্তি।",
            planetaryImpactEn = "Planetary remnants orbit cooled dead stellar corpses in absolute darkness and absolute zero.",
            planetaryImpactBn = "পরম শূন্য তাপমাত্রায় চির অন্ধকারে মহাবিশ্ব এক অনন্ত মহাজাগতিক স্তব্ধতায় নিমজ্জিত হবে।",
            timeSliderRatio = 1.0f,
            accentColor = Color(0xFF78909C),
            speedMultiplier = 0.05f
        )
    )

    fun getEpochById(id: String): CosmicEpoch? = epochs.find { it.id == id }
}
