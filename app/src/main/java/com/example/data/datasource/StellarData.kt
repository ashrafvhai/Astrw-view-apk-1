package com.example.data.datasource

import androidx.compose.ui.graphics.Color
import com.example.data.model.*
import com.example.engine3d.Vector3D

object StellarData {

    // Major Stars and Constellations
    val constellations: List<Constellation> = listOf(
        Constellation(
            id = "orion",
            nameEn = "Orion",
            nameBn = "কালপুরুষ",
            englishMeaning = "The Hunter",
            banglaMeaning = "শিকারি যোদ্ধা",
            mythologyBn = "শীতকালীন আকাশের সবচেয়ে উজ্জ্বল ও পরিচিত নক্ষত্রমণ্ডল। এর কোমরবন্ধের তিনটি তারা (আলনিতাক, আলনিলাম ও মিনতাকা) 'কালপুরুষের কোমরবন্ধ' নামে পরিচিত।",
            seasonBn = "নভেম্বর – মার্চ (শীতকাল)",
            stars = listOf(
                Star3D("betelgeuse", "Betelgeuse", "আর্দ্রা (বেটেলজুস)", 0.5f, Vector3D(-70f, -120f, 180f), Color(0xFFFF7043), "M1-2 Ia-ab (Red Supergiant)"),
                Star3D("bellatrix", "Bellatrix", "বেলাট্রিক্স", 1.6f, Vector3D(70f, -110f, 175f), Color(0xFF90CAF9), "B2 III (Blue Giant)"),
                Star3D("alnitak", "Alnitak", "আলনিতাক", 1.7f, Vector3D(-35f, -10f, 190f), Color(0xFF80D8FF), "O9.7 Ib"),
                Star3D("alnilam", "Alnilam", "আলনিলাম", 1.7f, Vector3D(0f, 0f, 195f), Color(0xFF80D8FF), "B0 Ia"),
                Star3D("mintaka", "Mintaka", "মিনতাকা", 2.2f, Vector3D(35f, 10f, 190f), Color(0xFF80D8FF), "O9.5 II"),
                Star3D("saiph", "Saiph", "সাইফ", 2.0f, Vector3D(-60f, 110f, 180f), Color(0xFF82B1FF), "B0.5 Ia"),
                Star3D("rigel", "Rigel", "বাণরাজা (রাইজেল)", 0.1f, Vector3D(65f, 120f, 185f), Color(0xFF00E5FF), "B8 Ia (Blue Supergiant)")
            ),
            lines = listOf(
                0 to 1, // Betelgeuse to Bellatrix (Shoulders)
                0 to 2, // Betelgeuse to Alnitak
                1 to 4, // Bellatrix to Mintaka
                2 to 3, // Alnitak to Alnilam (Belt)
                3 to 4, // Alnilam to Mintaka (Belt)
                2 to 5, // Alnitak to Saiph
                4 to 6, // Mintaka to Rigel
                5 to 6  // Saiph to Rigel (Feet)
            )
        ),
        Constellation(
            id = "ursa_major",
            nameEn = "Ursa Major",
            nameBn = "সপ্তর্ষি মণ্ডল",
            englishMeaning = "The Great Bear",
            banglaMeaning = "সাত মহাজ্ঞানী ঋষি",
            mythologyBn = "উত্তর গোলার্ধের সবচেয়ে বিখ্যাত তারা সমষ্টি। সাতটি উজ্জ্বল তারা মিলে একটি পানির পাত্র বা হাতা তৈরি করে। সামনের দুটি তারা দিয়ে ধ্রুবতারা (Polaris) সহজেই খুঁজে পাওয়া যায়।",
            seasonBn = "সারা বছর দৃশ্যমান (উত্তর আকাশ)",
            stars = listOf(
                Star3D("dubhe", "Dubhe", "ক্রতু", 1.8f, Vector3D(40f, -180f, 120f), Color(0xFFFFD54F), "K0 III"),
                Star3D("merak", "Merak", "পুলহ", 2.4f, Vector3D(45f, -140f, 130f), Color(0xFFE0E0E0), "A1 V"),
                Star3D("phecda", "Phecda", "পুলস্ত্য", 2.4f, Vector3D(10f, -135f, 140f), Color(0xFFE0E0E0), "A0 Ve"),
                Star3D("megrez", "Megrez", "অত্রি", 3.3f, Vector3D(10f, -170f, 130f), Color(0xFFE0E0E0), "A3 V"),
                Star3D("alioth", "Alioth", "অঙ্গিরা", 1.8f, Vector3D(-30f, -165f, 140f), Color(0xFFE0E0E0), "A1 III-IVp"),
                Star3D("mizar", "Mizar", "বশিষ্ঠ", 2.2f, Vector3D(-65f, -175f, 130f), Color(0xFFE0E0E0), "A2 V"),
                Star3D("alkaid", "Alkaid", "মরীচি", 1.8f, Vector3D(-100f, -195f, 110f), Color(0xFF82B1FF), "B3 V")
            ),
            lines = listOf(
                0 to 1, // Dubhe - Merak (Pointers to North Star)
                1 to 2, // Merak - Phecda
                2 to 3, // Phecda - Megrez
                3 to 0, // Megrez - Dubhe (Bowl complete)
                3 to 4, // Megrez - Alioth (Handle start)
                4 to 5, // Alioth - Mizar
                5 to 6  // Mizar - Alkaid
            )
        ),
        Constellation(
            id = "scorpius",
            nameEn = "Scorpius",
            nameBn = "বৃশ্চিক মণ্ডল",
            englishMeaning = "The Scorpion",
            banglaMeaning = "বিচ্ছু",
            mythologyBn = "গ্রীষ্মকালীন দক্ষিণ আকাশের সবচেয়ে নজরকাড়া তারামণ্ডল। এর হৃদয়ে রয়েছে রক্তিম দানব তারা 'জ্যেষ্ঠা' (Antares)। লেজের বাঁকানো অংশটি ঠিক বিচ্ছুর বিষাক্ত হুলের মতো।",
            seasonBn = "মে – সেপ্টেম্বর (গ্রীষ্মকাল)",
            stars = listOf(
                Star3D("graffias", "Graffias", "গ্রাফিয়াস", 2.6f, Vector3D(-30f, 150f, -120f), Color(0xFF90CAF9), "B0.5 V"),
                Star3D("dschubba", "Dschubba", "জুব্বা", 2.3f, Vector3D(-45f, 160f, -110f), Color(0xFF90CAF9), "B0.3 IV"),
                Star3D("antares", "Antares", "জ্যেষ্ঠা (অ্যান্টারেস)", 0.9f, Vector3D(-20f, 180f, -100f), Color(0xFFFF5252), "M1.5 Iab (Red Supergiant)"),
                Star3D("sargas", "Sargas", "সার্গাস", 1.8f, Vector3D(10f, 220f, -80f), Color(0xFFFFD54F), "F1 II"),
                Star3D("shaula", "Shaula", "মূলা (শাউলা)", 1.6f, Vector3D(40f, 235f, -90f), Color(0xFF80D8FF), "B2 IV (Scorpion Stinger)")
            ),
            lines = listOf(
                0 to 1,
                1 to 2,
                2 to 3,
                3 to 4
            )
        ),
        Constellation(
            id = "cassiopeia",
            nameEn = "Cassiopeia",
            nameBn = "শর্মিষ্ঠা",
            englishMeaning = "The Queen",
            banglaMeaning = "রাণী শর্মিষ্ঠা",
            mythologyBn = "উত্তর আকাশে ইংরেজি 'W' অথবা 'M' অক্ষরের মতো দৃশ্যমান নক্ষত্রমণ্ডল। এটি মিল্কিওয়ে ছায়াপথের ঘন নক্ষত্রবলয়ের ওপর ভেসে থাকে।",
            seasonBn = "শরৎ ও শীতকালীন উত্তর আকাশ",
            stars = listOf(
                Star3D("caph", "Caph", "কাফ", 2.3f, Vector3D(110f, -190f, 40f), Color(0xFFFFECB3), "F2 III"),
                Star3D("schedar", "Schedar", "শেদার", 2.2f, Vector3D(80f, -210f, 30f), Color(0xFFFFB74D), "K0 IIIa"),
                Star3D("gamma_cas", "Navi", "নাভি", 2.1f, Vector3D(50f, -200f, 40f), Color(0xFF80D8FF), "B0.5 IVe"),
                Star3D("ruchbah", "Ruchbah", "রুচবাহ", 2.7f, Vector3D(20f, -220f, 35f), Color(0xFFFFFFFF), "A5 V"),
                Star3D("segin", "Segin", "সেগিন", 3.4f, Vector3D(-10f, -210f, 45f), Color(0xFF82B1FF), "B7 III")
            ),
            lines = listOf(
                0 to 1,
                1 to 2,
                2 to 3,
                3 to 4
            )
        ),
        Constellation(
            id = "canis_major",
            nameEn = "Canis Major",
            nameBn = "মহা-শ্বান মণ্ডল",
            englishMeaning = "The Greater Dog",
            banglaMeaning = "বৃহৎ কুকুর",
            mythologyBn = "এই মণ্ডলে রয়েছে পুরো নৈশ আকাশের সবচেয়ে উজ্জ্বলতম নক্ষত্র 'লুব্ধক' (Sirius)। কালপুরুষের ঠিক পেছনে অবস্থিত শিকারি কুকুরের প্রতীক।",
            seasonBn = "ডিসেম্বর – এপ্রিল",
            stars = listOf(
                Star3D("sirius", "Sirius", "লুব্ধক (সিরিয়াস)", -1.46f, Vector3D(-110f, -30f, 170f), Color(0xFFE1F5FE), "A1 V (Brightest Star in Night Sky)"),
                Star3D("mirzam", "Mirzam", "মির্জাম", 2.0f, Vector3D(-140f, -20f, 160f), Color(0xFF80D8FF), "B1 II-III"),
                Star3D("wezen", "Wezen", "ওয়েজেন", 1.8f, Vector3D(-120f, 50f, 150f), Color(0xFFFFD54F), "F8 Ia"),
                Star3D("adhara", "Adhara", "আধারা", 1.5f, Vector3D(-95f, 65f, 160f), Color(0xFF82B1FF), "B2 II")
            ),
            lines = listOf(
                1 to 0,
                0 to 2,
                2 to 3
            )
        )
    )

    // Deep Space Wonders
    val deepSpaceWonders: List<DeepSpaceWonder> = listOf(
        DeepSpaceWonder(
            id = "sagittarius_a_star",
            nameEn = "Sagittarius A*",
            nameBn = "ধনু রাশি এ* (সুপারম্যাসিভ ব্ল্যাক হোল)",
            category = WonderCategory.BLACK_HOLE,
            distanceLightYears = "২৬,০০০ আলোকবর্ষ",
            constellation = "ধনু রাশি (Sagittarius)",
            subtitleBn = "আমাদের মিল্কিওয়ে ছায়াপথের কেন্দ্রস্থলের অতল গহ্বর",
            descriptionBn = "আমাদের গ্যালাক্সির ঠিক কেন্দ্রস্থলে অবস্থিত এই মহাদানবীয় কৃষ্ণগহ্বরের ভর প্রায় ৪০ লক্ষ সূর্যের সমান! এর মহাকর্ষ এতই প্রচণ্ড যে আলো পর্যন্ত বের হতে পারে না। ২০২২ সালে ইভেন্ট হরাইজন টেলিস্কোপ প্রথমবার এর ছবি ধারণ করে।",
            whyItMattersBn = "এটি পুরো মিল্কিওয়ে ছায়াপথের কোটি কোটি নক্ষত্রকে তার তীব্র মহাকর্ষীয় অক্ষের চারপাশে ধরে রেখেছে। এর ঘটনা দিগন্তের (Event Horizon) ভেতরে সময় ও মহাশূন্যের পরিচিত নিয়ম বিলুপ্ত হয়ে যায়।",
            visualPosition = Vector3D(10f, 80f, -190f),
            primaryColor = Color(0xFFFF6D00),
            accentColor = Color(0xFF00E5FF)
        ),
        DeepSpaceWonder(
            id = "pillars_of_creation",
            nameEn = "Pillars of Creation (M16)",
            nameBn = "সৃষ্টির স্তম্ভ (পিলার্স অফ ক্রিয়েশন)",
            category = WonderCategory.NEBULA,
            distanceLightYears = "৬,৫০০ আলোকবর্ষ",
            constellation = "সর্প মণ্ডল (Serpens)",
            subtitleBn = "ঈগল নীহারিকার বুকে নতুন নক্ষত্রের জন্মশালা",
            descriptionBn = "আন্তঃনাক্ষত্রিক গ্যাস ও মহাজাগতিক ধূলিকণার সুবিশাল স্তম্ভ, যা দেখতে যেন এক জাদুকরী আঙুল। হাবল ও জেমস ওয়েব স্পেস টেলিস্কোপের তোলা সবচেয়ে মহিমান্বিত ছবিগুলোর মধ্যে এটি অন্যতম। এই স্তম্ভগুলোর উচ্চতা প্রায় ৪ থেকে ৫ আলোকবর্ষ!",
            whyItMattersBn = "এখানে নতুন নতুন ভ্রূণ-নক্ষত্র (Protostars) জন্ম নিচ্ছে। মহাকর্ষীয় চাপে গ্যাস সংকুচিত হয়ে সেখানে নতুন সৌরজগতের পত্তন ঘটছে।",
            visualPosition = Vector3D(90f, 60f, -140f),
            primaryColor = Color(0xFF00B0FF),
            accentColor = Color(0xFFFFB300)
        ),
        DeepSpaceWonder(
            id = "andromeda_galaxy",
            nameEn = "Andromeda Galaxy (M31)",
            nameBn = "অ্যান্ড্রোমিডা ছায়াপথ (এম৩১)",
            category = WonderCategory.GALAXY,
            distanceLightYears = "২৫ লক্ষ আলোকবর্ষ",
            constellation = "দেবযানী (Andromeda)",
            subtitleBn = "আমাদের নিকটতম বিশালাকার সর্পিল ছায়াপথ",
            descriptionBn = "প্রায় ১ ট্রিলিয়ন (১ লক্ষ কোটি) নক্ষত্রের এক বিশাল সর্পিল ছায়াপথ। পরিষ্কার অন্ধকার রাতে কোনো টেলিস্কোপ ছাড়াই খালি চোখে মানুষ যত দূর দেখতে পারে—অ্যান্ড্রোমিডা হচ্ছে মহাবিশ্বের সেই দূরতম বস্তু!",
            whyItMattersBn = "প্রতি সেকেন্ডে প্রায় ১১০ কিমি বেগে এটি আমাদের মিল্কিওয়ের দিকে ছুটে আসছে। প্রায় ৪.৫ বিলিয়ন বছর পর দুটি ছায়াপথ মিলে তৈরি হবে এক নতুন মহা-ছায়াপথ: 'মিল্কোমিডা' (Milkdromeda)!",
            visualPosition = Vector3D(-120f, -140f, 80f),
            primaryColor = Color(0xFF7C4DFF),
            accentColor = Color(0xFFFF80AB)
        ),
        DeepSpaceWonder(
            id = "crab_nebula",
            nameEn = "Crab Nebula (M1)",
            nameBn = "কর্কট নীহারিকা (ক্র্যাব নেবুলা)",
            category = WonderCategory.PULSAR,
            distanceLightYears = "৬,৫০০ আলোকবর্ষ",
            constellation = "বৃষ রাশি (Taurus)",
            subtitleBn = "১০৫৪ সালের ঐতিহাসিক সুপারনোভা বিস্ফোরণের প্রতিচ্ছবি",
            descriptionBn = "১০৫৪ খ্রিস্টাব্দে চীনের জ্যোতির্বিজ্ঞানীরা আকাশে এমন এক উজ্জ্বল নতুন তারা দেখেছিলেন যা দিনের বেলাতেও ২৩ দিন ধরে দৃশ্যমান ছিল! সেটি ছিল একটি নক্ষত্রের সুপারনোভা মৃত্যু। সেই বিস্ফোরণের অবশিষ্টাংশই আজকের ক্র্যাব নেবুলা।",
            whyItMattersBn = "এর কেন্দ্রে রয়েছে একটি নিউট্রন তারা বা পালসার—যা প্রতি সেকেন্ডে ৩০ বার নিজের অক্ষে লাটিমের মতো বনবন করে ঘুরছে এবং রেডিও তরঙ্গের আলোকরশ্মি নিক্ষেপ করছে।",
            visualPosition = Vector3D(-80f, -70f, 150f),
            primaryColor = Color(0xFF00E676),
            accentColor = Color(0xFFFF1744)
        ),
        DeepSpaceWonder(
            id = "jwst_deep_field",
            nameEn = "Webb's First Deep Field",
            nameBn = "জেমস ওয়েব ডিপ ফিল্ড (SMACS 0723)",
            category = WonderCategory.GALAXY,
            distanceLightYears = "৪.৬ থেকে ১৩.১ বিলিয়ন আলোকবর্ষ",
            constellation = "উড়ন্ত মাছ (Volans)",
            subtitleBn = "আকাশে ধূলিকণার সমান জায়গায় হাজার হাজার আদিম ছায়াপথ",
            descriptionBn = "হাতের মুঠোয় এক কণা বালু ধরে আকাশের দিকে তাকালে সেই বালুকণা যতটুকু আকাশ ঢাকে—জেমস ওয়েব টেলিস্কোপ সেই অতি ক্ষুদ্র অংশে হাজার হাজার ছায়াপথের নিখুঁত চিত্র তুলে এনেছে, যা মহাবিশ্বের শৈশবকালের আলো!",
            whyItMattersBn = "সামনের গ্যালাক্সি ক্লাস্টারের প্রচণ্ড মাধ্যাকর্ষণ পেছনের দূরবর্তী ছায়াপথগুলোর আলোকে বাঁকিয়ে বিবর্ধিত করে—যাকে মহাকর্ষীয় লেন্সিং (Gravitational Lensing) বলে।",
            visualPosition = Vector3D(140f, 120f, -90f),
            primaryColor = Color(0xFFFFAB00),
            accentColor = Color(0xFFD500F9)
        )
    )

    // Cosmic Mysteries Dossier
    val cosmicMysteries: List<CosmicMysteryTopic> = listOf(
        CosmicMysteryTopic(
            id = "dark_matter_energy",
            titleEn = "Dark Matter & Dark Energy",
            titleBn = "ডার্ক ম্যাটার ও ডার্ক এনার্জি: মহাবিশ্বের ৯৫% অজানা!",
            categoryBn = "মহাজাগতিক শূন্যতা",
            overviewBn = "আমরা যে কোটি কোটি তারা, গ্রহ, নীহারিকা এবং মানুষ দেখি—তা মহাবিশ্বের মাত্র ৫%! বাকি ২৭% অদৃশ্য ডার্ক ম্যাটার এবং ৬৮% রহস্যময় ডার্ক এনার্জি দিয়ে গঠিত, যার প্রকৃতি বিজ্ঞানীদের কাছে আজও এক পরম বিস্ময়।",
            currentTheoriesBn = listOf(
                "ডার্ক ম্যাটার আলোর সাথে কোনো মিথস্ক্রিয়া করে না, তবে এর মহাকর্ষ ছায়াপথগুলোকে বিচ্ছিন্ন হয়ে ছড়িয়ে যাওয়া থেকে আটকে রাখে।",
                "ডার্ক এনার্জি পুরো মহাবিশ্বকে ক্রমেই আরও দ্রুতগতিতে প্রসারিত (Accelerating Expansion) করে তুলছে।",
                "সম্ভাব্য কণা: WIMP (Weakly Interacting Massive Particles) অথবা অ্যাক্সিয়ন (Axion) কণা।"
            ),
            mindBlowingFactBn = "আপনি যেখানে বসে আছেন, প্রতি সেকেন্ডে আপনার শরীরের ভেতর দিয়ে ট্রিলিয়ন ট্রিলিয়ন ডার্ক ম্যাটার কণা কোনো স্পর্শ ছাড়াই চলে যাচ্ছে!",
            iconKey = "dark_energy",
            accentColor = Color(0xFF7C4DFF)
        ),
        CosmicMysteryTopic(
            id = "black_hole_singularity",
            titleEn = "Black Hole Singularity & Information Paradox",
            titleBn = "সিঙ্গুলারিটি ও ইনফরমেশন প্যারাডক্স",
            categoryBn = "মহাকর্ষের সীমান্ত",
            overviewBn = "ব্ল্যাক হোলের কেন্দ্রে পদার্থের ঘনত্ব অসীম হয়ে যায়, যেখানে পদার্থবিজ্ঞানের সমস্ত সাধারণ সূত্র ভেঙে পড়ে। সবচেয়ে বড় বিতর্ক: কোনো বস্তু ব্ল্যাক হোলে পড়লে তার ভেতরের কোয়ান্টাম তথ্য কি চিরতরে মুছে যায়?",
            currentTheoriesBn = listOf(
                "স্টিফেন হকিংয়ের হকিং রেডিয়েশন ধারণা দেয় যে ব্ল্যাক হোল ধীরে ধীরে বাষ্পীভূত হতে পারে।",
                "হোলোগ্রাফিক প্রিন্সিপল: ভেতরের তথ্য হয়তো ব্ল্যাক হোলের দ্বিমাত্রিক ইভেন্ট হরাইজনের পৃষ্ঠে সংরক্ষিত থাকে।",
                "কোয়ান্টাম গ্র্যাভিটি বা স্ট্রিং থিওরি或许 সিঙ্গুলারিটির প্রকৃত স্বরূপ উন্মোচন করতে পারবে।"
            ),
            mindBlowingFactBn = "একটি ব্ল্যাক হোলের ইভেন্ট হরাইজনে সময় এতটাই ধীরে চলে যে বাইরের পর্যবেক্ষকের কাছে মনে হবে আপনি সেখানে চিরকালের জন্য জমে গেছেন!",
            iconKey = "singularity",
            accentColor = Color(0xFFFF6D00)
        ),
        CosmicMysteryTopic(
            id = "fermi_paradox",
            titleEn = "The Fermi Paradox & The Great Filter",
            titleBn = "ফার্মি প্যারাডক্স: মহাবিশ্বে সবাই কোথায়?",
            categoryBn = "ভিনগ্রহী প্রাণ ও সভ্যতা",
            overviewBn = "মহাবিশ্বে শত শত কোটি বাসযোগ্য গ্রহ থাকা সত্ত্বেও আমরা আজ পর্যন্ত কোনো উন্নত বুদ্ধিমান সভ্যতার সংকেত পাইনি কেন? নোবেলজয়ী এনরিকো ফার্মি প্রশ্ন করেছিলেন: 'সবাই কোথায়?'",
            currentTheoriesBn = listOf(
                "দ্য গ্রেট ফিল্টার (The Great Filter): জৈব অণু থেকে আন্তঃনাক্ষত্রিক সভ্যতায় পৌঁছানোর পথে কোনো এক মারাত্মক প্রাকৃতিক বাধা রয়েছে।",
                "ডার্ক ফরেস্ট হাইপোথিসিস (The Dark Forest): মহাবিশ্ব এক অন্ধকার অরণ্য, যেখানে প্রতিটি সভ্যতা বেঁচে থাকার জন্য নিজেদের লুকিয়ে রাখে।",
                "জু হাইপোথিসিস: উন্নত কোনো সভ্যতা হয়তো আমাদের পর্যবেক্ষণ করছে কিন্তু আমাদের স্বাভাবিক বিকাশে হস্তক্ষেপ করছে না।"
            ),
            mindBlowingFactBn = "যদি গ্রেট ফিল্টার আমাদের পেছনে থাকে (যেমন প্রাণের সূচনা হওয়াটাই অতি বিরল), তবে আমরা ভাগ্যবান! কিন্তু যদি ফিল্টারটি আমাদের সামনে থাকে (যেমন পারমাণবিক যুদ্ধ বা এআই ধ্বংস), তবে মানবজাতির ভবিষ্যৎ বিপন্ন!",
            iconKey = "fermi",
            accentColor = Color(0xFF00E5FF)
        ),
        CosmicMysteryTopic(
            id = "multiverse_hypothesis",
            titleEn = "The Multiverse Hypothesis",
            titleBn = "মাল্টিভার্স বা সমান্তরাল মহাবিশ্ব",
            categoryBn = "মহাজাগতিক সৃষ্টিতত্ত্ব",
            overviewBn = "আমাদের মহাবিশ্ব কি একমাত্র? নাকি এক অনন্ত বুদবুদ সমুদ্রের মতো অসংখ্য সমান্তরাল মহাবিশ্ব বিদ্যমান, যার প্রতিটিতে পদার্থবিজ্ঞানের নিয়ম সম্পূর্ণ আলাদা হতে পারে?",
            currentTheoriesBn = listOf(
                "কসমিক ইনফ্লেশন তত্ত্ব: বিগ ব্যাংয়ের সময় স্থান-কালের চিরন্তন স্ফীতি অবিরাম নতুন নতুন মহাবিশ্ব তৈরি করছে।",
                "কোয়ান্টাম মেকানিক্সের মেনি-ওয়ার্ল্ডস ইন্টারপ্রিটেশন: প্রতিটি কোয়ান্টাম সিদ্ধান্তের সাথে সাথে মহাবিশ্ব নতুন শাখায় বিভক্ত হয়।",
                "ফাইন-টিউনিং রহস্য: আমাদের মহাবিশ্বের মৌলিক ধ্রুবকগুলো প্রাণের জন্য এত নিখুঁতভাবে সামঞ্জস্যপূর্ণ কেন?"
            ),
            mindBlowingFactBn = "মাল্টিভার্স যদি সত্য হয়, তবে অন্য কোনো সমান্তরাল মহাবিশ্বে আপনার অবিকল একটি রূপ হয়তো এখন মহাকাশচারী হয়ে মঙ্গল গ্রহে হেঁটে বেড়াচ্ছে!",
            iconKey = "multiverse",
            accentColor = Color(0xFFFF4081)
        ),
        CosmicMysteryTopic(
            id = "rogue_planets",
            titleEn = "Rogue Planets: Wanderers of the Void",
            titleBn = "রোগ প্ল্যানেট: অভিভাবকহীন যাযাবর গ্রহ",
            categoryBn = "অতল মহাশূন্য",
            overviewBn = "যেসব গ্রহ কোনো নক্ষত্রকে প্রদক্ষিণ করে না, বরং নক্ষত্রমণ্ডলীর প্রচণ্ড অভিকর্ষীয় ধাক্কায় নিজ সৌরজগৎ থেকে ছিটকে গিয়ে চির অন্ধকার মহাশূন্যে একা ভেসে বেড়ায়। ধারণা করা হয়, মিল্কিওয়ের তারার চেয়েও যাযাবর গ্রহের সংখ্যা বেশি!",
            currentTheoriesBn = listOf(
                "নক্ষত্র ব্যবস্থার প্রাথমিক বিশৃঙ্খলার সময় বিশাল গ্যাস দানবদের মহাকর্ষের টানে ছোট পাথুরে গ্রহগুলো ছিটকে যায়।",
                "কোনো সূর্য না থাকা সত্ত্বেও পুরু বায়ুমণ্ডল বা ভূগর্ভস্থ তেজস্ক্রিয় উত্তাপের কারণে এদের মাটির নিচে বরফের নিচে তরল সমুদ্র থাকতে পারে।",
                "রোমান স্পেস টেলিস্কোপ মাইক্রোলেন্সিং প্রযুক্তির মাধ্যমে হাজার হাজার রোগ প্ল্যানেট শনাক্ত করবে।"
            ),
            mindBlowingFactBn = "মিল্কিওয়ে ছায়াপথেই অন্তত শত শত কোটি যাযাবর গ্রহ কোনো আলো বা উষ্ণতা ছাড়াই পরম শূন্য তাপমাত্রার মহাকাশে নীরবে ভেসে চলেছে!",
            iconKey = "rogue_planet",
            accentColor = Color(0xFF69F0AE)
        )
    )

    // Upcoming Celestial Events / Stargazer Calendar
    val spaceEvents: List<SpaceEvent> = listOf(
        SpaceEvent(
            dateOrSeason = "আগস্ট ১২-১৩",
            titleBn = "পারসিডস উল্কাবৃষ্টি (Perseids)",
            titleEn = "Perseid Meteor Shower",
            descriptionBn = "সুইফট-টাটল ধূমকেতুর রেখে যাওয়া ধ্বংসাবশেষ পৃথিবীর বায়ুমণ্ডলে প্রবেশ করে ঘণ্টায় প্রায় ৬০-১০০টি উজ্জ্বল উল্কা তৈরি করে।",
            viewingTipBn = "মধ্যরাতের পর শহরের কৃত্রিম আলো থেকে দূরে কোনো খোলা প্রান্তরে উত্তর-পূর্ব আকাশে তাকান।"
        ),
        SpaceEvent(
            dateOrSeason = "ডিসেম্বর ১৩-১৪",
            titleBn = "জেমিনিডস উল্কাবৃষ্টি (Geminids)",
            titleEn = "Geminid Meteor Shower",
            descriptionBn = "বছরের সবচেয়ে শক্তিশালী উল্কাবৃষ্টি। ৩২০০ ফেথন গ্রহাণু থেকে সৃষ্ট উজ্জ্বল, রঙিন ও ধীরগতির উল্কাপাত ঘটে।",
            viewingTipBn = "ঘণ্টায় ১২০টিরও বেশি উল্কা দেখা যেতে পারে, বৃষ ও মিথুন রাশির দিকে লক্ষ্য রাখুন।"
        ),
        SpaceEvent(
            dateOrSeason = "বসন্ত ও শরৎকাল",
            titleBn = "মহাজাগতিক বিষুব (Equinox)",
            titleEn = "Vernal & Autumnal Equinox",
            descriptionBn = "সূর্য সরাসরি পৃথিবীর বিষুবরেখার উপর অবস্থান করে, ফলে পৃথিবীর সর্বত্র দিন ও রাত প্রায় সমান ১২ ঘণ্টার হয়।",
            viewingTipBn = "সৌরজগতের অক্ষীয় হেলে পড়ার প্রভাব বোঝার চমৎকার সময়।"
        ),
        SpaceEvent(
            dateOrSeason = "নভেম্বর ২০২৬",
            titleBn = "বুধ গ্রহের ট্রানজিট ও গ্রহসমাবেশ",
            titleEn = "Planetary Alignment",
            descriptionBn = "শুক্র, মঙ্গল এবং বৃহস্পতি ভোরবেলা একই সরলরেখায় খুব কাছাকাছি উজ্জ্বল অবস্থায় দৃশ্যমান হবে।",
            viewingTipBn = "সূর্যোদয়ের ১ ঘণ্টা আগে পূর্ব দিগন্তে তাকালে খালি চোখেই তিনটি উজ্জ্বল গ্রহ একসাথে দেখা যাবে।"
        )
    )
}
