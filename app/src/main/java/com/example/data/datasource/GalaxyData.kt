package com.example.data.datasource

import androidx.compose.ui.graphics.Color
import com.example.engine3d.Vector3D
import com.example.ui.localization.AppLanguage

enum class GalaxyType(val labelBn: String, val labelEn: String) {
    SPIRAL("সর্পিল ছায়াপথ", "Spiral Galaxy"),
    BARRED_SPIRAL("দণ্ডযুক্ত সর্পিল ছায়াপথ", "Barred Spiral"),
    ELLIPTICAL("উপবৃত্তাকার ছায়াপথ", "Elliptical Galaxy"),
    IRREGULAR("অনিয়মিত ছায়াপথ", "Irregular Galaxy"),
    RING_GALAXY("বলয়াকৃতি ছায়াপথ", "Ring Galaxy"),
    STARBURST("স্টারবার্স্ট ছায়াপথ", "Starburst Galaxy")
}

data class GalaxyTarget(
    val id: String,
    val nameEn: String,
    val nameBn: String,
    val type: GalaxyType,
    val distanceLightYearsEn: String,
    val distanceLightYearsBn: String,
    val diameterLightYearsEn: String,
    val diameterLightYearsBn: String,
    val estimatedStarsEn: String,
    val estimatedStarsBn: String,
    val constellationBn: String,
    val constellationEn: String,
    val subtitleEn: String,
    val subtitleBn: String,
    val descriptionEn: String,
    val descriptionBn: String,
    val cosmicMysteriesEn: List<String>,
    val cosmicMysteriesBn: List<String>,
    val fascinatingFactsEn: List<String>,
    val fascinatingFactsBn: List<String>,
    val position3D: Vector3D,
    val coreColor: Color,
    val armColor: Color,
    val diskTiltRad: Float = 0.35f,
    val visualRadiusDp: Float = 32f
) {
    fun getSubtitle(lang: AppLanguage): String = if (lang == AppLanguage.ENGLISH) subtitleEn else subtitleBn
    fun getDescription(lang: AppLanguage): String = if (lang == AppLanguage.ENGLISH) descriptionEn else descriptionBn
    fun getDistance(lang: AppLanguage): String = if (lang == AppLanguage.ENGLISH) distanceLightYearsEn else distanceLightYearsBn
    fun getDiameter(lang: AppLanguage): String = if (lang == AppLanguage.ENGLISH) diameterLightYearsEn else diameterLightYearsBn
    fun getStars(lang: AppLanguage): String = if (lang == AppLanguage.ENGLISH) estimatedStarsEn else estimatedStarsBn
    fun getConstellation(lang: AppLanguage): String = if (lang == AppLanguage.ENGLISH) constellationEn else constellationBn
    fun getMysteries(lang: AppLanguage): List<String> = if (lang == AppLanguage.ENGLISH) cosmicMysteriesEn else cosmicMysteriesBn
    fun getFacts(lang: AppLanguage): List<String> = if (lang == AppLanguage.ENGLISH) fascinatingFactsEn else fascinatingFactsBn
}

object GalaxyData {

    val galaxies: List<GalaxyTarget> = listOf(
        GalaxyTarget(
            id = "milky_way",
            nameEn = "Milky Way Galaxy",
            nameBn = "আকাশগঙ্গা ছায়াপথ",
            type = GalaxyType.BARRED_SPIRAL,
            distanceLightYearsEn = "0 Light Years (Our Cosmic Home)",
            distanceLightYearsBn = "০ আলোকবর্ষ (আমাদের নিজস্ব মহাজাগতিক বাড়ি)",
            diameterLightYearsEn = "100,000 Light Years",
            diameterLightYearsBn = "১,০০,০০০ আলোকবর্ষ",
            estimatedStarsEn = "100 to 400 Billion Stars",
            estimatedStarsBn = "১০,০০০ থেকে ৪০,০০০ কোটি নক্ষত্র",
            constellationBn = "ধনু রাশি (কেন্দ্র)",
            constellationEn = "Sagittarius (Core)",
            subtitleEn = "Our barred spiral cosmic home sheltering our Solar System",
            subtitleBn = "আমাদের সৌরমণ্ডলের আশ্রয়দাতা বিশালাকার সর্পিল ছায়াপথ",
            descriptionEn = "Our home barred spiral galaxy where our Sun orbits in the Orion Arm. At its center lies Sagittarius A*, a supermassive black hole around which our entire galaxy rotates once every 230 million years (a Cosmic Year).",
            descriptionBn = "আমাদের নিজস্ব ছায়াপথ যার ওরিয়ন স্পারে সূর্য এবং আমরা অবস্থান করছি। এর কেন্দ্রস্থলে অবস্থিত সুপারম্যাসিভ ব্ল্যাক হোল 'ধনু রাশি এ*' (Sagittarius A*) যার চারপাশে পুরো গ্যালাক্সি প্রতি ২২ কোটি বছরে একবার লাটিমের মতো পাক খায়।",
            cosmicMysteriesEn = listOf(
                "Dark Matter Halo: Over 90% of the Milky Way's mass is invisible dark matter that prevents the galaxy from flying apart.",
                "Fermi Bubbles: Giant gamma-ray bubbles extending 25,000 light years above and below the galactic plane, remnants of ancient central black hole eruptions."
            ),
            cosmicMysteriesBn = listOf(
                "ডার্ক ম্যাটার হ্যালো: গ্যালাক্সির দৃশ্যমান নক্ষত্রের তুলনায় এর ৯০% ভর অদৃশ্য ডার্ক ম্যাটার দ্বারা বেষ্টিত, যা একে ভেঙে যাওয়া থেকে আটকে রাখে।",
                "ফার্মি বাবলস: আকাশগঙ্গার কেন্দ্রের ওপর ও নিচে প্রায় ২৫,০০০ আলোকবর্ষ উঁচু দুটি বিশাল গামা-রশ্মির বুদবুদ রয়েছে, যা প্রাচীন ব্ল্যাক হোলের বিস্ফোরণের ফল হতে পারে।"
            ),
            fascinatingFactsEn = listOf(
                "Traveling at the speed of light (300,000 km/s), it would still take 100,000 years to cross from one edge of the Milky Way to the other!",
                "Stars near the central black hole reach speeds of several thousand kilometers per second."
            ),
            fascinatingFactsBn = listOf(
                "আমরা যদি সেকেন্ডে আলোর গতিতে (৩ লক্ষ কিমি) ভ্রমণ করি, তবে আকাশগঙ্গার এক প্রান্ত থেকে অন্য প্রান্তে যেতে ১ লক্ষ বছর সময় লাগবে!",
                "আকাশগঙ্গার কেন্দ্রস্থলের নক্ষত্রগুলো প্রতি সেকেন্ডে কয়েক হাজার কিলোমিটার গতিতে ছোটাছুটি করছে।"
            ),
            position3D = Vector3D(0f, 0f, 0f),
            coreColor = Color(0xFFFFD54F),
            armColor = Color(0xFF80D8FF),
            diskTiltRad = 0.45f,
            visualRadiusDp = 48f
        ),
        GalaxyTarget(
            id = "andromeda_m31",
            nameEn = "Andromeda Galaxy (M31)",
            nameBn = "অ্যান্ড্রোমিডা ছায়াপথ (এম৩১)",
            type = GalaxyType.SPIRAL,
            distanceLightYearsEn = "2.537 Million Light Years",
            distanceLightYearsBn = "২৫ লক্ষ আলোকবর্ষ",
            diameterLightYearsEn = "220,000 Light Years",
            diameterLightYearsBn = "২,২০,০০০ আলোকবর্ষ",
            estimatedStarsEn = "Approx. 1 Trillion Stars",
            estimatedStarsBn = "প্রায় ১ লক্ষ কোটি (১ ট্রিলিয়ন) তারা",
            constellationBn = "দেবযানী মণ্ডল",
            constellationEn = "Andromeda",
            subtitleEn = "Our giant spiral neighbour and largest galaxy in the Local Group",
            subtitleBn = "আমাদের সবচেয়ে কাছের বিশালাকার সর্পিল প্রতিবেশী",
            descriptionEn = "The largest galaxy in the Local Group cluster. Andromeda is the most distant celestial object visible to the naked human eye under a dark sky. Light we see from Andromeda today left it 2.5 million years ago.",
            descriptionBn = "লোকাল গ্রুপ গ্যালাক্সি ক্লাস্টারের বৃহত্তম ছায়াপথ। অন্ধকার রাতে খালি চোখে মানুষ যত দূর দেখতে পারে—অ্যান্ড্রোমিডা হচ্ছে মহাবিশ্বের সেই পরম দূরবর্তী দৃশ্যমান বস্তু।",
            cosmicMysteriesEn = listOf(
                "Milkdromeda Collision: Andromeda is heading towards the Milky Way at 110 km/s. In 4.5 billion years, both galaxies will merge to form a giant elliptical galaxy!",
                "Double Nucleus: Andromeda possesses two bright nuclear peaks, evidence that it devoured another galaxy in the cosmic past."
            ),
            cosmicMysteriesBn = listOf(
                "মহাজাগতিক সংঘর্ষ (Milkdromeda): অ্যান্ড্রোমিডা প্রতি সেকেন্ডে ১১০ কিমি বেগে আমাদের আকাশগঙ্গার দিকে ছুটে আসছে। প্রায় ৪.৫ বিলিয়ন বছর পর এই দুই গ্যালাক্সির মহামিলনে তৈরি হবে নতুন এক অতিকায় ছায়াপথ!",
                "দ্বৈত নিউক্লিয়াস রহস্য: অ্যান্ড্রোমিডার কেন্দ্রে দুটি আলাদা উজ্জ্বল আলোককেন্দ্র রয়েছে, যা অতীতে অন্য কোনো ছোট ছায়াপথ গিলে ফেলার প্রমাণ।"
            ),
            fascinatingFactsEn = listOf(
                "Andromeda spans more than twice the diameter of our Milky Way!",
                "At least 14 satellite dwarf galaxies orbit Andromeda."
            ),
            fascinatingFactsBn = listOf(
                "অ্যান্ড্রোমিডার ব্যাস আমাদের আকাশগঙ্গার চেয়ে দ্বিগুণেরও বেশি চওড়া!",
                "এর চারপাশে অন্তত ১৪টি ছোট বামন ছায়াপথ উপগ্রহের মতো প্রদক্ষিণ করছে।"
            ),
            position3D = Vector3D(-680f, -420f, 850f),
            coreColor = Color(0xFFFFF176),
            armColor = Color(0xFFB388FF),
            diskTiltRad = 0.55f,
            visualRadiusDp = 44f
        ),
        GalaxyTarget(
            id = "triangulum_m33",
            nameEn = "Triangulum Galaxy (M33)",
            nameBn = "ট্রায়াঙ্গুলাম ছায়াপথ (এম৩৩)",
            type = GalaxyType.SPIRAL,
            distanceLightYearsEn = "2.73 Million Light Years",
            distanceLightYearsBn = "২৭.৩ লক্ষ আলোকবর্ষ",
            diameterLightYearsEn = "60,000 Light Years",
            diameterLightYearsBn = "৬০,০০০ আলোকবর্ষ",
            estimatedStarsEn = "Approx. 40 Billion Stars",
            estimatedStarsBn = "প্রায় ৪,০০০ কোটি তারা",
            constellationBn = "ত্রিকোণ মণ্ডল",
            constellationEn = "Triangulum",
            subtitleEn = "Active star factory and third largest member of the Local Group",
            subtitleBn = "লোকাল গ্রুপের তৃতীয় বৃহত্তম নক্ষত্র কারখানা",
            descriptionEn = "The third-largest galaxy in the Local Group. Surprisingly, astronomers have not detected any supermassive black hole at its core. Its spiral arms are teeming with vigorous star birth.",
            descriptionBn = "লোকাল গ্রুপের তৃতীয় প্রধান সদস্য। এতে কোনো সুপারম্যাসিভ ব্ল্যাক হোল এখনো শনাক্ত হয়নি, যা জ্যোতির্বিজ্ঞানীদের কাছে এক পরম বিস্ময়। এর সর্পিল বাহুগুলোতে দানবীয় নীল নক্ষত্র তৈরির অবিরাম উৎসব চলছে।",
            cosmicMysteriesEn = listOf(
                "Absence of Central Supermassive Black Hole: Most large galaxies have huge central black holes, but Triangulum has either none or only an intermediate one under 3,000 solar masses.",
                "NGC 604 Giant Nebula: A gigantic star-forming region 1,500 light-years across, one of the largest stellar nurseries known."
            ),
            cosmicMysteriesBn = listOf(
                "কেন্দ্রীয় ব্ল্যাক হোলের অনুপস্থিতি: অধিকাংশ গ্যালাক্সির কেন্দ্রে কোটি সূর্যের ভরের ব্ল্যাক হোল থাকলেও ট্রায়াঙ্গুলামের কেন্দ্রে মাত্র ৩,০০০ সূর্যের ভরের ক্ষুদ্র ব্ল্যাক হোল থাকতে পারে অথবা এটি একেবারেই অনুপস্থিত!",
                "এনজিসি ৬০৪ (NGC 604) দানব নীহারিকা: এর ভেতরে প্রায় ১৫০০ আলোকবর্ষ চওড়া এক নক্ষত্র তৈরির মহাজাগতিক নার্সারি রয়েছে।"
            ),
            fascinatingFactsEn = listOf(
                "Triangulum is gravitationally bound to Andromeda and will participate in the future Milkdromeda cosmic merger."
            ),
            fascinatingFactsBn = listOf(
                "ভবিষ্যতে অ্যান্ড্রোমিডা ও আকাশগঙ্গার সংঘর্ষের সময় ট্রায়াঙ্গুলামও হয়তো এদের সাথে যুক্ত হয়ে মহাবিস্ফোরণ ঘটাবে।"
            ),
            position3D = Vector3D(-540f, 620f, -790f),
            coreColor = Color(0xFF80DEEA),
            armColor = Color(0xFF00E5FF),
            diskTiltRad = 0.28f,
            visualRadiusDp = 34f
        ),
        GalaxyTarget(
            id = "whirlpool_m51",
            nameEn = "Whirlpool Galaxy (M51)",
            nameBn = "ঘূর্ণি ছায়াপথ (এম৫১)",
            type = GalaxyType.SPIRAL,
            distanceLightYearsEn = "23 Million Light Years",
            distanceLightYearsBn = "২ কোটি ৩০ লক্ষ আলোকবর্ষ",
            diameterLightYearsEn = "76,000 Light Years",
            diameterLightYearsBn = "৭৬,০০০ আলোকবর্ষ",
            estimatedStarsEn = "Approx. 100 Billion Stars",
            estimatedStarsBn = "প্রায় ১০,০০০ কোটি তারা",
            constellationBn = "শিকারী কুকুর মণ্ডল",
            constellationEn = "Canes Venatici",
            subtitleEn = "The universe's most immaculate grand-design spiral galaxy",
            subtitleBn = "মহাবিশ্বের সবচেয়ে নিখুঁত গ্র্যান্ড-ডিজাইন সর্পিল ছায়াপথ",
            descriptionEn = "Renowned for its breathtaking grand-design spiral structure. One of its spiral arms is physically interacting with a smaller companion galaxy, NGC 5195, forming a cosmic tidal bridge of glowing gas.",
            descriptionBn = "আকাশের সবচেয়ে রাজকীয় ও নিখুঁত প্যাটার্নের সর্পিল গ্যালাক্সি। এর একটি সর্পিল বাহু পাশের ছোট সঙ্গী ছায়াপথ NGC 5195-কে স্পর্শ করে আছে। মহাকর্ষীয় জোয়ার-ভাটার টানে এদের মধ্যে গ্যাসীয় সেতু তৈরি হয়েছে।",
            cosmicMysteriesEn = listOf(
                "Density Waves: Gravitational interactions with NGC 5195 generate density waves that maintain its pristine spiral arm structure."
            ),
            cosmicMysteriesBn = listOf(
                "মহাকর্ষীয় তরঙ্গের নাচন: ছোট সঙ্গী গ্যালাক্সিটির ধাক্কায় এম৫১-এর গ্যালাকটিক ডিস্কে মহাকর্ষীয় তরঙ্গ সৃষ্টি হয়েছে, যা নিখুঁত সর্পিল বাহুগুলোকে অক্ষুণ্ণ রেখেছে।"
            ),
            fascinatingFactsEn = listOf(
                "In 1845, Lord Rosse used the world's first great reflecting telescope to discover its spiral nature."
            ),
            fascinatingFactsBn = listOf(
                "১৮৪৫ সালে লর্ড রস বিশ্বের প্রথম বৃহৎ টেলিস্কোপ দিয়ে এই গ্যালাক্সির সর্পিল রূপ পর্যবেক্ষণ করেছিলেন।"
            ),
            position3D = Vector3D(820f, -510f, 640f),
            coreColor = Color(0xFFFFD54F),
            armColor = Color(0xFF8C9EFF),
            diskTiltRad = 0.12f,
            visualRadiusDp = 38f
        ),
        GalaxyTarget(
            id = "sombrero_m104",
            nameEn = "Sombrero Galaxy (M104)",
            nameBn = "সোমব্রেরো ছায়াপথ (এম১০৪)",
            type = GalaxyType.SPIRAL,
            distanceLightYearsEn = "29 Million Light Years",
            distanceLightYearsBn = "২ কোটি ৯০ লক্ষ আলোকবর্ষ",
            diameterLightYearsEn = "50,000 Light Years",
            diameterLightYearsBn = "৫০,০০০ আলোকবর্ষ",
            estimatedStarsEn = "Approx. 80 Billion Stars",
            estimatedStarsBn = "প্রায় ৮,০০০ কোটি তারা",
            constellationBn = "কন্যা রাশি",
            constellationEn = "Virgo",
            subtitleEn = "Iconic bright central bulge framed by a dramatic dark dust lane",
            subtitleBn = "মেক্সিকান টুপির ন্যায় উজ্জ্বল বাল্জ ও গাঢ় ধূলির বলয়",
            descriptionEn = "Famous for its resemblance to a broad-brimmed Mexican sombrero. It boasts an extraordinarily massive central stellar bulge and a striking equatorial dust lane.",
            descriptionBn = "দেখতে ঠিক মেক্সিকান সোমব্রেরো টুপির মতো। এর কেন্দ্রস্থলের নক্ষত্রপুঞ্জের স্ফীতি (Central Bulge) অস্বাভাবিকভাবে সুবিশাল এবং এর বিষুবীয় অঞ্চলে একটি চমৎকার কালো ধূলির রিং রয়েছে।",
            cosmicMysteriesEn = listOf(
                "1-Billion-Solar-Mass Monster: Houses an enormous supermassive black hole weighing nearly 1 billion times the mass of our Sun."
            ),
            cosmicMysteriesBn = listOf(
                "১০০ কোটি সূর্যের ভরের দানব: এর কেন্দ্রস্থলে সূর্যের ভরের প্রায় ১০০ কোটি গুণ ভারী একটি চরম মহাদানবীয় ব্ল্যাক হোল ঘাপটি মেরে বসে আছে!"
            ),
            fascinatingFactsEn = listOf(
                "Contains nearly 2,000 globular star clusters, over 10 times more than the Milky Way!"
            ),
            fascinatingFactsBn = listOf(
                "সোমব্রেরো ছায়াপথ প্রায় ২,০০০টি গ্লোবুলার ক্লাস্টার নক্ষত্রমণ্ডলী ধারণ করে, যা আমাদের আকাশগঙ্গার চেয়ে ১০ গুণ বেশি!"
            ),
            position3D = Vector3D(910f, 680f, -580f),
            coreColor = Color(0xFFFFF9C4),
            armColor = Color(0xFFFFCC80),
            diskTiltRad = 0.78f,
            visualRadiusDp = 36f
        ),
        GalaxyTarget(
            id = "large_magellanic_cloud",
            nameEn = "Large Magellanic Cloud (LMC)",
            nameBn = "বৃহৎ মেগেলানিক মেঘ",
            type = GalaxyType.IRREGULAR,
            distanceLightYearsEn = "163,000 Light Years",
            distanceLightYearsBn = "১ লক্ষ ৬৩ হাজার আলোকবর্ষ",
            diameterLightYearsEn = "14,000 Light Years",
            diameterLightYearsBn = "১৪,০০০ আলোকবর্ষ",
            estimatedStarsEn = "Approx. 20 Billion Stars",
            estimatedStarsBn = "প্রায় ২,০০০ কোটি তারা",
            constellationBn = "ডলফিন ও সোনালি মাছ মণ্ডল",
            constellationEn = "Dorado / Mensa",
            subtitleEn = "Nearest bright satellite dwarf galaxy to the Milky Way",
            subtitleBn = "আকাশগঙ্গার নিকটতম উজ্জ্বল উপগ্রহ ছায়াপথ",
            descriptionEn = "One of the closest satellite dwarf galaxies to our Milky Way, visible to the naked eye from the Southern Hemisphere as a luminous celestial cloud.",
            descriptionBn = "আমাদের আকাশগঙ্গার অন্যতম নিকটতম বামন উপগ্রহ ছায়াপথ। দক্ষিণ গোলার্ধের আকাশ থেকে খালি চোখেই কুয়াশার মতো দেখা যায়।",
            cosmicMysteriesEn = listOf(
                "Tarantula Nebula: Houses the most luminous and violent star-forming complex in the entire Local Group."
            ),
            cosmicMysteriesBn = listOf(
                "টারান্টুলা নীহারিকা (Tarantula Nebula): পুরো লোকাল গ্রুপের সবচেয়ে বৃহত্তম এবং চরম সক্রিয় নক্ষত্র তৈরির মহাজাগতিক নার্সারি এখানেই অবস্থিত।"
            ),
            fascinatingFactsEn = listOf(
                "Host of Supernova 1987A, the closest observed naked-eye supernova in 400 years!"
            ),
            fascinatingFactsBn = listOf(
                "১৯৮৭ সালে এখানে 'সুপারনোভা ১৯৮৭এ' বিস্ফোরিত হয়েছিল, যা গত ৪০০ বছরের মধ্যে পৃথিবীর সবচেয়ে কাছে ঘটা খালি চোখে দৃশ্যমান সুপারনোভা!"
            ),
            position3D = Vector3D(-410f, 820f, 320f),
            coreColor = Color(0xFF80D8FF),
            armColor = Color(0xFFFF80AB),
            diskTiltRad = 0.4f,
            visualRadiusDp = 30f
        ),
        GalaxyTarget(
            id = "cigar_galaxy_m82",
            nameEn = "Cigar Galaxy (M82)",
            nameBn = "চুরুট ছায়াপথ (এম৮২)",
            type = GalaxyType.STARBURST,
            distanceLightYearsEn = "12 Million Light Years",
            distanceLightYearsBn = "১ কোটি ২০ লক্ষ আলোকবর্ষ",
            diameterLightYearsEn = "37,000 Light Years",
            diameterLightYearsBn = "৩৭,০০০ আলোকবর্ষ",
            estimatedStarsEn = "Approx. 30 Billion Stars",
            estimatedStarsBn = "প্রায় ৩,০০০ কোটি তারা",
            constellationBn = "সপ্তর্ষি মণ্ডল",
            constellationEn = "Ursa Major",
            subtitleEn = "Fierce starburst galaxy venting brilliant red hydrogen superwinds",
            subtitleBn = "তীব্র লাল গ্যাসীয় বিস্ফোরণে গর্জে ওঠা স্টারবার্স্ট গ্যালাক্সি",
            descriptionEn = "A prototypical starburst galaxy producing new stars at 10 times the rate of the Milky Way, driven by gravitational tides from companion M81.",
            descriptionBn = "এটি একটি উগ্র স্টারবার্স্ট ছায়াপথ, যেখানে আমাদের আকাশগঙ্গার চেয়ে ১০ গুণ দ্রুতগতিতে নতুন নতুন নক্ষত্র জন্ম নিচ্ছে।",
            cosmicMysteriesEn = listOf(
                "Superwind Blast: Hundreds of simultaneous supernova detonations vent blazing hydrogen plumes tens of thousands of light-years into deep space."
            ),
            cosmicMysteriesBn = listOf(
                "সুপারউইন্ড ব্লাস্ট: অবিরাম শত শত সুপারনোভা বিস্ফোরণের সম্মিলিত শক্তিতে একটি মহাজাগতিক 'সুপারউইন্ড' এর কেন্দ্র থেকে বাইরের মহাশূন্যে গ্যাসীয় ঝড় তৈরি করেছে।"
            ),
            fascinatingFactsEn = listOf(
                "In infrared wavelengths, M82 is one of the brightest galaxies in the entire northern sky."
            ),
            fascinatingFactsBn = listOf(
                "ইনফ্রারেড আলোতে দেখলে এটি মহাকাশের অন্যতম উজ্জ্বলতম ও জ্বলন্ত বস্তুর মতো প্রতীয়মান হয়।"
            ),
            position3D = Vector3D(-780f, -720f, -480f),
            coreColor = Color(0xFFFF5252),
            armColor = Color(0xFFFF7043),
            diskTiltRad = 0.82f,
            visualRadiusDp = 32f
        ),
        GalaxyTarget(
            id = "cartwheel_galaxy",
            nameEn = "Cartwheel Galaxy",
            nameBn = "রথচক্র ছায়াপথ (কার্টহুইল)",
            type = GalaxyType.RING_GALAXY,
            distanceLightYearsEn = "500 Million Light Years",
            distanceLightYearsBn = "৫০ কোটি আলোকবর্ষ",
            diameterLightYearsEn = "150,000 Light Years",
            diameterLightYearsBn = "১,৫০,০০০ আলোকবর্ষ",
            estimatedStarsEn = "Tens of Billions of Stars",
            estimatedStarsBn = "কয়েক হাজার কোটি তারা",
            constellationBn = "ভাস্কর মণ্ডল",
            constellationEn = "Sculptor",
            subtitleEn = "Cosmic bullseye collision creating an enormous ring of new stars",
            subtitleBn = "মহাজাগতিক মুখোমুখি সংঘর্ষে সৃষ্ট জ্বলন্ত রথচক্র",
            descriptionEn = "Formed when a smaller intruder galaxy punched directly through the core of a large spiral disk, creating a ripple shockwave like a stone dropped in a pond.",
            descriptionBn = "একটি বিশাল সর্পিল ছায়াপথের ঠিক কেন্দ্র ভেদ করে অন্য একটি ছোট ছায়াপথ আঘাত করায় এই বিরল রিং গ্যালাক্সির সৃষ্টি হয়েছে।",
            cosmicMysteriesEn = listOf(
                "Cosmic Shockwave: The collision shockwave is still expanding outwards through space at 200 km/s!"
            ),
            cosmicMysteriesBn = listOf(
                "মহাজাগতিক শকওয়েভ: প্রায় ২০ কোটি বছর আগে ঘটা এই সংঘর্ষের ঢেউ এখনো বাইরের দিকে প্রতি সেকেন্ডে ২০০ কিমি বেগে সম্প্রসারিত হচ্ছে!"
            ),
            fascinatingFactsEn = listOf(
                "The James Webb Space Telescope recently revealed its intricate dusty spokes and central rotating ring in exquisite detail."
            ),
            fascinatingFactsBn = listOf(
                "জেমস ওয়েব স্পেস টেলিস্কোপ এর ধূলিময় কঙ্কাল ও কেন্দ্রের ঘূর্ণনশীল স্পোকের ছবি অতি সূক্ষ্মভাবে তুলে ধরেছে।"
            ),
            position3D = Vector3D(1050f, 280f, 920f),
            coreColor = Color(0xFFFF4081),
            armColor = Color(0xFF00E5FF),
            diskTiltRad = 0.32f,
            visualRadiusDp = 35f
        ),
        GalaxyTarget(
            id = "pinwheel_m101",
            nameEn = "Pinwheel Galaxy (M101)",
            nameBn = "পিনহুইল ছায়াপথ (এম১০১)",
            type = GalaxyType.SPIRAL,
            distanceLightYearsEn = "21 Million Light Years",
            distanceLightYearsBn = "২ কোটি ১০ লক্ষ আলোকবর্ষ",
            diameterLightYearsEn = "170,000 Light Years",
            diameterLightYearsBn = "১,৭০,০০০ আলোকবর্ষ",
            estimatedStarsEn = "Approx. 1 Trillion Stars",
            estimatedStarsBn = "প্রায় ১ লক্ষ কোটি (১ ট্রিলিয়ন) তারা",
            constellationBn = "সপ্তর্ষি মণ্ডল",
            constellationEn = "Ursa Major",
            subtitleEn = "Colossal grand spiral disk 70% larger than the Milky Way",
            subtitleBn = "আকাশগঙ্গার চেয়ে দ্বিগুণ আকৃতির রাজকীয় সর্পিল চক্র",
            descriptionEn = "A gigantic face-on spiral galaxy containing over 3,000 giant H II starburst regions where young blue O and B-type stars are constantly being born.",
            descriptionBn = "আমাদের আকাশগঙ্গার চেয়ে প্রায় ৭০% বড় আকৃতির এক দানবীয় ফেস-অন স্পাইরাল গ্যালাক্সি।",
            cosmicMysteriesEn = listOf(
                "Asymmetric Spiral Arms: Tidal friction from companion galaxies has stretched its spiral arms on one side far beyond normal bounds."
            ),
            cosmicMysteriesBn = listOf(
                "অসমমিত বাহু রহস্য: এক পাশের সর্পিল বাহুগুলো অন্য পাশের চেয়ে অনেক বেশি দূর পর্যন্ত প্রসারিত।"
            ),
            fascinatingFactsEn = listOf(
                "At least 5 visible supernovae have been detected within M101 over the past century."
            ),
            fascinatingFactsBn = listOf(
                "গত এক শতকে এতে অন্তত ৫টি দৃশ্যমান সুপারনোভা বিস্ফোরণ শনাক্ত করা হয়েছে!"
            ),
            position3D = Vector3D(420f, -940f, -650f),
            coreColor = Color(0xFFE040FB),
            armColor = Color(0xFF64FFDA),
            diskTiltRad = 0.15f,
            visualRadiusDp = 40f
        ),
        GalaxyTarget(
            id = "jwst_cosmic_web",
            nameEn = "JWST Deep Cosmic Web (JADES)",
            nameBn = "দূরতম মহাজাগতিক জালিকা (জেডস)",
            type = GalaxyType.ELLIPTICAL,
            distanceLightYearsEn = "13.4 Billion Light Years",
            distanceLightYearsBn = "১,৩৪০ কোটি আলোকবর্ষ",
            diameterLightYearsEn = "Vast Cosmic Web Cluster",
            diameterLightYearsBn = "অবিরাম বিস্তৃত গ্যালাক্সি ক্লাস্টার",
            estimatedStarsEn = "Countless Earliest Primeval Galaxies",
            estimatedStarsBn = "অগণিত কোটি কোটি প্রাচীনতম গ্যালাক্সি",
            constellationBn = "ফার্নেস মণ্ডল",
            constellationEn = "Fornax",
            subtitleEn = "Primeval infant galaxies born just 290 million years after the Big Bang",
            subtitleBn = "বিগ ব্যাং-এর শৈশবকালের আদিমতম ছায়াপথসমূহ",
            descriptionEn = "Light captured from when the universe was only 2% of its current age! Deep infrared spectroscopy from the James Webb Space Telescope uncovered these primordial galaxy seeds.",
            descriptionBn = "মহাবিশ্বের সৃষ্টির মাত্র ২৯ কোটি বছর পরের আলো! জেমস ওয়েব স্পেস টেলিস্কোপ দূরতম মহাকাশের চরম ইনফ্রারেড আলো বিশ্লেষণ করে এই আদিম গ্যালাক্সি শনাক্ত করেছে।",
            cosmicMysteriesEn = listOf(
                "Early Mass Paradox: How could such massive and luminous galaxies form so quickly after the Big Bang, challenging standard cosmology models?"
            ),
            cosmicMysteriesBn = listOf(
                "প্রারম্ভিক ভর প্যারাডক্স: মহাবিশ্বের সৃষ্টির এত কম সময়ের মধ্যে কীভাবে এত ভারী ও উজ্জ্বল ছায়াপথ গঠিত হতে পারল?"
            ),
            fascinatingFactsEn = listOf(
                "Looking at these galaxies is looking 13.4 billion years into the deep cosmic past!"
            ),
            fascinatingFactsBn = listOf(
                "আমরা এই ছায়াপথগুলোর যে ছবি আজ দেখছি, তা আজ থেকে ১৩.৪ বিলিয়ন বছর আগের দৃশ্য!"
            ),
            position3D = Vector3D(-1100f, 850f, -950f),
            coreColor = Color(0xFFFF6D00),
            armColor = Color(0xFFFFD54F),
            diskTiltRad = 0.25f,
            visualRadiusDp = 28f
        )
    )

    fun getGalaxyById(id: String): GalaxyTarget? {
        return galaxies.find { it.id.equals(id, ignoreCase = true) }
    }
}
