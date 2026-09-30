package com.example.data.datasource

import androidx.compose.ui.graphics.Color
import com.example.engine3d.Vector3D
import com.example.ui.localization.AppLanguage

enum class ExoplanetType(val labelEn: String, val labelBn: String) {
    SUPER_EARTH("Super-Earth", "সুপার-আর্থ"),
    HOT_JUPITER("Hot Jupiter", "উত্তপ্ত বৃহস্পতি"),
    HYCEAN_WORLD("Hycean Ocean World", "হায়সিয়ান মহাসাগর গ্রহ"),
    CIRCUMBINARY("Circumbinary (Twin Suns)", "দ্বৈত নক্ষত্র পরিক্রমী"),
    LAVA_DIAMOND("Molten Diamond Planet", "গলিত হীরা গ্রহ"),
    SUB_NEPTUNE("Sub-Neptune", "সাব-নেপচুন")
}

data class ExoplanetTarget(
    val id: String,
    val name: String,
    val type: ExoplanetType,
    val distanceLightYearsEn: String,
    val distanceLightYearsBn: String,
    val hostStarEn: String,
    val hostStarBn: String,
    val discoveryTelescope: String,
    val habitabilityScore: String, // e.g. "85% (Earth Similarity 0.85)"
    val orbitalPeriodDays: Double,
    val surfaceConditionEn: String,
    val surfaceConditionBn: String,
    val descriptionEn: String,
    val descriptionBn: String,
    val cosmicWonderFactEn: String,
    val cosmicWonderFactBn: String,
    val primaryColor: Color,
    val secondaryColor: Color,
    val glowColor: Color,
    val hasRings: Boolean = false,
    val position3D: Vector3D
) {
    fun getDistance(lang: AppLanguage): String = if (lang == AppLanguage.ENGLISH) distanceLightYearsEn else distanceLightYearsBn
    fun getHostStar(lang: AppLanguage): String = if (lang == AppLanguage.ENGLISH) hostStarEn else hostStarBn
    fun getSurface(lang: AppLanguage): String = if (lang == AppLanguage.ENGLISH) surfaceConditionEn else surfaceConditionBn
    fun getDescription(lang: AppLanguage): String = if (lang == AppLanguage.ENGLISH) descriptionEn else descriptionBn
    fun getWonderFact(lang: AppLanguage): String = if (lang == AppLanguage.ENGLISH) cosmicWonderFactEn else cosmicWonderFactBn
}

object ExoplanetData {

    val exoplanets: List<ExoplanetTarget> = listOf(
        ExoplanetTarget(
            id = "trappist_1e",
            name = "TRAPPIST-1e",
            type = ExoplanetType.SUPER_EARTH,
            distanceLightYearsEn = "39.46 Light Years",
            distanceLightYearsBn = "৩৯.৪৬ আলোকবর্ষ",
            hostStarEn = "TRAPPIST-1 (Ultra-cool Red Dwarf)",
            hostStarBn = "ট্রাপিস্ট-১ (অতি-শীতল লাল বামন)",
            discoveryTelescope = "Spitzer & TRAPPIST",
            habitabilityScore = "0.85 (Prime Earth Twin)",
            orbitalPeriodDays = 6.1,
            surfaceConditionEn = "Temperate rocky surface with high probability of liquid water oceans under a crimson sky",
            surfaceConditionBn = "নাতিশীতোষ্ণ পাথুরে ভূখণ্ড, রক্তিম আকাশের নিচে তরল জলের সমুদ্র থাকার প্রবল সম্ভাবনা",
            descriptionEn = "One of the most promising habitable worlds known to science. TRAPPIST-1e has nearly the exact same radius and density as Earth, receiving 66% of the solar flux Earth receives, making its climate exceptionally stable.",
            descriptionBn = "মহাবিশ্বে বিজ্ঞানীদের আবিষ্কৃত সবচেয়ে সম্ভাবনাময় পার্থিব যমজ গ্রহ। এর ভর ও আকার পৃথিবীর প্রায় সমান। লাল বামন নক্ষত্রটির শান্ত আলোয় এর পৃষ্ঠে তরল পানি ও বায়ুমণ্ডল বজায় থাকার জোরালো প্রমাণ মিলেছে।",
            cosmicWonderFactEn = "From its surface, 6 sibling planets appear in the sky as luminous giant orbs, larger than the Moon appears from Earth!",
            cosmicWonderFactBn = "এর মাটিতে দাঁড়িয়ে আকাশে তাকালে বাকি ৬টি প্রতিবেশী গ্রহকে চাঁদের চেয়েও বিশালাকার ঝলমলে গোলকের মতো দেখা যাবে!",
            primaryColor = Color(0xFF29B6F6),
            secondaryColor = Color(0xFF81C784),
            glowColor = Color(0xFF00E5FF),
            position3D = Vector3D(-520f, 340f, 480f)
        ),
        ExoplanetTarget(
            id = "proxima_b",
            name = "Proxima Centauri b",
            type = ExoplanetType.SUPER_EARTH,
            distanceLightYearsEn = "4.24 Light Years (Closest to Earth)",
            distanceLightYearsBn = "৪.২৪ আলোকবর্ষ (পৃথিবীর নিকটতম বহির্গ্রহ)",
            hostStarEn = "Proxima Centauri (Alpha Centauri System)",
            hostStarBn = "প্রক্সিমা সেন্টরাই (আলফা সেন্টরাই ব্যবস্থা)",
            discoveryTelescope = "ESO HARPS Spectrograph",
            habitabilityScore = "0.87 (Interstellar Neighbor)",
            orbitalPeriodDays = 11.2,
            surfaceConditionEn = "Tidally locked rocky world with an eternal twilight zone where water could pool",
            surfaceConditionBn = "জোয়ারবাঁধা পাথুরে জগৎ; দিন ও রাতের মিলনসীমায় চিরন্তন গোধূলি অঞ্চলে তরল পানির হ্রদ থাকতে পারে",
            descriptionEn = "Our closest interstellar neighbor outside the Solar System. It orbits within the habitable zone of our nearest star, completing a full year in just 11.2 Earth days.",
            descriptionBn = "সৌরজগতের বাইরে মানবজাতির সবচেয়ে কাছের প্রতিবেশী বহির্গ্রহ। এটি তার নক্ষত্রের বাসযোগ্য অঞ্চলের ভেতর প্রদক্ষিণ করছে। মানুষের তৈরি ভবিষ্যৎ ইন্টারস্টেলার রোবোটিক অভিযানের প্রথম লক্ষ্যবস্তু!",
            cosmicWonderFactEn = "Light from Proxima b takes only 4.2 years to reach our eyes—it is humanity's primary target for our first interstellar robotic probes!",
            cosmicWonderFactBn = "এখান থেকে আলো পৃথিবীতে আসতে মাত্র ৪ বছর ২ মাস সময় নেয়। ভবিষ্যৎ ইন্টারস্টেলার ভ্রমণের জন্য এটিই মানুষের প্রধান গন্তব্য।",
            primaryColor = Color(0xFFFF7043),
            secondaryColor = Color(0xFF4DB6AC),
            glowColor = Color(0xFFFFAB40),
            position3D = Vector3D(460f, -220f, 380f)
        ),
        ExoplanetTarget(
            id = "k2_18b",
            name = "K2-18b",
            type = ExoplanetType.HYCEAN_WORLD,
            distanceLightYearsEn = "124 Light Years",
            distanceLightYearsBn = "১২৪ আলোকবর্ষ",
            hostStarEn = "K2-18 (Red Dwarf in Leo)",
            hostStarBn = "কে২-১৮ (সিংহ রাশি)",
            discoveryTelescope = "Kepler & James Webb Space Telescope",
            habitabilityScore = "0.73 (Hycean Ocean Candidate)",
            orbitalPeriodDays = 33.0,
            surfaceConditionEn = "Hydrogen-rich atmosphere enveloping a boiling global ocean with potential biosignature candidates",
            surfaceConditionBn = "হাইড্রোজেনে সমৃদ্ধ বায়ুমণ্ডলের নিচে বিস্তৃত ফুটন্ত বৈশ্বিক মহাসাগর; সম্ভাব্য জৈব-চিহ্ন শনাক্ত",
            descriptionEn = "In 2023, the James Webb Space Telescope detected carbon-bearing molecules (methane and carbon dioxide) and potential traces of dimethyl sulfide (DMS)—a compound produced almost exclusively by living phytoplankton on Earth!",
            descriptionBn = "২০২৩ সালে জেমস ওয়েব স্পেস টেলিস্কোপ এই গ্রহের বায়ুমণ্ডলে মিথেন ও কার্বন ডাই-অক্সাইডের সাথে 'ডাইমিথাইল সালফাইড' (DMS)-এর সম্ভাব্য উপস্থিতি শনাক্ত করেছে, যা পৃথিবীতে কেবল সামুদ্রিক জীবন্ত ফাইটোপ্ল্যাঙ্কটন থেকে উৎপন্ন হয়!",
            cosmicWonderFactEn = "K2-18b is the first candidate Hycean world: a whole new class of ocean-covered planets that could harbor extraterrestrial marine life.",
            cosmicWonderFactBn = "এটি পৃথিবীর বিজ্ঞানীদের পরিচিত প্রথম 'হায়সিয়ান' গ্রহ—যার পুরো পৃষ্ঠ ঢেকে রাখা গভীর মহাসাগরে এলিয়েন সামুদ্রিক প্রাণের সম্ভাবনা রয়েছে।",
            primaryColor = Color(0xFF00E5FF),
            secondaryColor = Color(0xFF7C4DFF),
            glowColor = Color(0xFF00B0FF),
            position3D = Vector3D(-640f, -510f, -420f)
        ),
        ExoplanetTarget(
            id = "wasp_76b",
            name = "WASP-76b",
            type = ExoplanetType.HOT_JUPITER,
            distanceLightYearsEn = "634 Light Years",
            distanceLightYearsBn = "৬৩৪ আলোকবর্ষ",
            hostStarEn = "WASP-76 (F-type Star in Pisces)",
            hostStarBn = "ডব্লিউএএসপি-৭৬ (মীন রাশি)",
            discoveryTelescope = "WASP-South & ESPRESSO",
            habitabilityScore = "0.00 (Extreme Inferno)",
            orbitalPeriodDays = 1.8,
            surfaceConditionEn = "Day side exceeds 2,400°C vaporizing iron; night side winds blast torrential molten liquid iron rain!",
            surfaceConditionBn = "দিনের তাপমাত্রা ২৪০০° সে. পার হয়ে লোহা বাষ্পীভূত হয়; রাতের দিকে বইতে থাকে ফুটন্ত তরল লোহার বৃষ্টির কালবৈশাখী ঝড়!",
            descriptionEn = "An ultra-hot tidally locked gas giant orbiting dangerously close to its host star. Strong winds blow iron vapor from the scorching day-side to the cooler night-side, where it condenses into molten iron droplets and rains from the sky.",
            descriptionBn = "মহাবিশ্বের সবচেয়ে ভয়ঙ্কর ও চরমভাবাপন্ন গ্যাস দানব। এর দিনের দিকের চরম তাপে লোহা বাষ্পে পরিণত হয় এবং রাতের দিকে প্রবল ঘূর্ণিবাতাসে সেই বাষ্প ঘনীভূত হয়ে আকাশ থেকে টপটপ করে ফুটন্ত গলিত লোহার বৃষ্টি হয়ে ঝরে পড়ে!",
            cosmicWonderFactEn = "A year on WASP-76b lasts only 43 hours! Its skies literally rain liquid iron droplets under hurricane-force winds.",
            cosmicWonderFactBn = "এখানে মাত্র ৪৩ ঘণ্টায় এক বছর পূর্ণ হয়! এর বায়ুমণ্ডলে বাতাসের গতিবেগ ঘণ্টায় ১৮,০০০ কিলোমিটারের বেশি।",
            primaryColor = Color(0xFFFF1744),
            secondaryColor = Color(0xFFFF9100),
            glowColor = Color(0xFFFF5252),
            position3D = Vector3D(580f, 620f, -480f)
        ),
        ExoplanetTarget(
            id = "cancri_55_e",
            name = "55 Cancri e (Janssen)",
            type = ExoplanetType.LAVA_DIAMOND,
            distanceLightYearsEn = "41 Light Years",
            distanceLightYearsBn = "৪১ আলোকবর্ষ",
            hostStarEn = "Copernicus (55 Cancri A)",
            hostStarBn = "কোপারনিকাস (৫৫ ক্যানক্রি এ)",
            discoveryTelescope = "Spitzer & James Webb",
            habitabilityScore = "0.02 (Diamond Crust Inferno)",
            orbitalPeriodDays = 0.74,
            surfaceConditionEn = "Covered in global oceans of boiling magma with a subterranean mantle composed of pure diamond!",
            surfaceConditionBn = "পৃষ্ঠদেশজুড়ে ফুটন্ত লাভাসমুদ্র এবং গভীর ভূ-অভ্যন্তরে শত শত ট্রিলিয়ন টন খাঁটি স্ফটিক হীরার স্তর!",
            descriptionEn = "Twice the size of Earth with 8 times its mass. High internal pressures and carbon-rich chemistry mean that up to a third of its interior is made of pure crystalline diamond, valued at trillions of times the world's GDP!",
            descriptionBn = "পৃথিবীর চেয়ে দ্বিগুণ আকারের এক চরম অতি-ঘন পাথুরে গ্রহ। এর বায়ুমণ্ডল ও গভীর অভ্যন্তরের রাসায়নিক গঠন কার্বনে ভরপুর। প্রচণ্ড চাপে এর অভ্যন্তরের এক-তৃতীয়াংশ খাঁটি হীরায় রূপান্তরিত হয়েছে বলে বিজ্ঞানীদের অভিমত।",
            cosmicWonderFactEn = "Its diamond core alone is estimated to be worth more than \$26.9 nonillion (\$26.9 x 10^30)—an unfathomable cosmic jewel!",
            cosmicWonderFactBn = "এর ভেতরের হীরার মূল্য সমগ্র পৃথিবীর মোট অর্থনীতির চেয়ে কোটি কোটি গুণ বেশি—মহাশূন্যে ভাসমান এক অবিশ্বাস্য রত্নভাণ্ডার!",
            primaryColor = Color(0xFFFFD700),
            secondaryColor = Color(0xFFFF3D00),
            glowColor = Color(0xFFFFEA00),
            position3D = Vector3D(-410f, 580f, 620f)
        ),
        ExoplanetTarget(
            id = "kepler_16b",
            name = "Kepler-16b (Tatooine)",
            type = ExoplanetType.CIRCUMBINARY,
            distanceLightYearsEn = "245 Light Years",
            distanceLightYearsBn = "২৪৫ আলোকবর্ষ",
            hostStarEn = "Kepler-16 A & B (Binary Star Pair)",
            hostStarBn = "কেপলার-১৬ এ ও বি (দ্বৈত নক্ষত্র)",
            discoveryTelescope = "NASA Kepler Space Telescope",
            habitabilityScore = "0.25 (Gas & Ice Circumbinary)",
            orbitalPeriodDays = 228.8,
            surfaceConditionEn = "Cold Saturn-mass gas world witnessing magnificent double sunrises and twin sunsets every day",
            surfaceConditionBn = "শীতল দ্বৈত নক্ষত্রমণ্ডল; প্রতিদিন আকাশে একসাথে দুটি সূর্যের উদয় ও সূর্যাস্তের অপূর্ব দৃশ্য দেখা যায়",
            descriptionEn = "The first confirmed circumbinary planet in human history—it orbits two stars simultaneously! The iconic double sunset of Luke Skywalker's Tatooine made real in our universe.",
            descriptionBn = "মানব ইতিহাসের প্রথম নিশ্চিত 'দ্বৈত নক্ষত্র পরিক্রমণকারী' বহির্গ্রহ! এটি একটি নয়, বরং মহাকর্ষীয়ভাবে আবদ্ধ দুটি নক্ষত্রের চারপাশে যুগপৎ প্রদক্ষিণ করে। সায়েন্স ফিকশনের মতো বাস্তবে এখানে আকাশে দুটি সূর্য অস্ত যায়।",
            cosmicWonderFactEn = "If you stood on an orbiting moon of Kepler-16b, you would cast two shadows of different colors!",
            cosmicWonderFactBn = "এর কোনো উপগ্রহে দাঁড়ালে আপনার শরীরে একই সাথে দুটি ভিন্ন রঙের ছায়া তৈরি হবে!",
            primaryColor = Color(0xFFFFB300),
            secondaryColor = Color(0xFF00E676),
            glowColor = Color(0xFFFFC400),
            hasRings = true,
            position3D = Vector3D(710f, -480f, -610f)
        ),
        ExoplanetTarget(
            id = "hd_189733b",
            name = "HD 189733b",
            type = ExoplanetType.HOT_JUPITER,
            distanceLightYearsEn = "64.5 Light Years",
            distanceLightYearsBn = "৬৪.৫ আলোকবর্ষ",
            hostStarEn = "HD 189733 (Orange Dwarf)",
            hostStarBn = "এইচডি ১৮৯৭৩৩ (কমলা বামন)",
            discoveryTelescope = "Haute-Provence & Hubble",
            habitabilityScore = "0.00 (Glass Weather Nightmare)",
            orbitalPeriodDays = 2.2,
            surfaceConditionEn = "Deep cobalt blue atmosphere tormented by 8,700 km/h winds blowing razor-sharp silicate glass rain sideways",
            surfaceConditionBn = "কোবাল্ট নীল আকাশ, যেখানে ঘণ্টায় ৮,৭০০ কিমি বেগে অনুভূমিকভাবে কাঁচের সূক্ষ্ম ছুরির মতো বৃষ্টি ও ঝড় বয়ে যায়",
            descriptionEn = "From orbit it looks like a peaceful blue marble, but its azure color comes not from oceans, but from a haze of silicate particles scattering blue light. Its supersonic winds blow molten glass shards horizontally through the atmosphere.",
            descriptionBn = "দূর থেকে দেখতে শান্ত নীল পৃথিবীর মতো মনোরম মনে হলেও এর নীল রঙ মহাসাগরের জন্য নয়, বরং বায়ুমণ্ডলে উড়ন্ত সিলিকন ও কাঁচের কণার কারণে। এখানে ঘণ্টায় ৮,৭০০ কিমি বেগে উড়ন্ত কাঁচের মারাত্মক সাইক্লোন অবিরাম তাণ্ডব চালায়।",
            cosmicWonderFactEn = "Winds blow at 7 times the speed of sound—getting caught in its atmosphere means death by a trillion flying shards of molten glass!",
            cosmicWonderFactBn = "শব্দের চেয়ে ৭ গুণ বেশি গতিতে এখানে বাতাস ছোটে—প্রকৃতির এক অবর্ণনীয় সুন্দর কিন্তু মারাত্মক কাঁচের ঘূর্ণিঝড়!",
            primaryColor = Color(0xFF0091EA),
            secondaryColor = Color(0xFF00B0FF),
            glowColor = Color(0xFF40C4FF),
            position3D = Vector3D(-680f, -320f, 680f)
        )
    )

    fun getExoplanetById(id: String): ExoplanetTarget? {
        return exoplanets.find { it.id.equals(id, ignoreCase = true) }
    }
}
