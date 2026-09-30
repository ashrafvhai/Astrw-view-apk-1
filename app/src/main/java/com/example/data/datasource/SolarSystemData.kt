package com.example.data.datasource

import androidx.compose.ui.graphics.Color
import com.example.data.model.*

object SolarSystemData {

    val celestialBodies: List<CelestialBody> = listOf(
        CelestialBody(
            id = "sun",
            nameEn = "Sun",
            nameBn = "সূর্য",
            type = CelestialType.STAR,
            tagLineEn = "The luminous nuclear heart of our solar system",
            tagLineBn = "সৌরজগতের প্রাণকেন্দ্র এবং আমাদের নিকটতম নক্ষত্র",
            baseRadiusDp = 38f,
            relativeScale = 2.4f,
            orbitDistanceUnits = 0f,
            orbitSpeedMultiplier = 0f,
            orbitInclinationDeg = 0f,
            axialTiltDeg = 7.25f,
            rotationSpeed = 0.3f,
            primaryColor = Color(0xFFFFB300),
            secondaryColor = Color(0xFFFF5722),
            glowColor = Color(0xFFFFD54F),
            atmosphereColor = Color(0x66FF9800),
            diameterKm = "1,392,700 km",
            massKg = "1.989 × 10³⁰ kg (99.86% of Solar System)",
            surfaceGravityMps2 = 274.0,
            dayLengthHours = "600 hours (25 Earth days)",
            yearLengthDays = "230 Million Earth years (Galactic Year)",
            averageTempC = "5,500°C (Surface) / 15,000,000°C (Core)",
            distanceFromSunMillionKm = "0 km",
            numberOfMoons = 0,
            atmosphereCompositionBn = listOf("হাইড্রোজেন (৭৩.৪%)", "হিলিয়াম (২৪.৮%)", "অক্সিজেন ও কার্বন (১.৮%)"),
            coreLayers = listOf(
                CoreLayer("Core", "পারমাণবিক কেন্দ্রস্থল", "০ - ২৫% ব্যাসার্ধ", "Nuclear fusion furnace converting H to He", "প্রতি সেকেন্ডে ৬০ কোটি টন হাইড্রোজেন হিলিয়ামে রূপান্তর হচ্ছে", Color(0xFFFFF176)),
                CoreLayer("Radiative Zone", "বিকিরণ বলয়", "২৫ - ৭০% ব্যাসার্ধ", "Photons take up to 100,000 years to escape", "ফোটন কণাগুলোর এই অঞ্চল পার হতে ১ লক্ষ বছর পর্যন্ত সময় লাগে", Color(0xFFFFB74D)),
                CoreLayer("Convection Zone", "পরিচলন বলয়", "৭০ - ১০০% ব্যাসার্ধ", "Boiling plasma cells carrying heat to surface", "ফুটন্ত প্লাজমার ঘূর্ণন যা পৃষ্ঠে তাপ ও চৌম্বক ক্ষেত্র তৈরি করে", Color(0xFFFF7043)),
                CoreLayer("Photosphere & Corona", "করোনা ও আলোকমণ্ডল", "পৃষ্ঠ ও বায়ুমণ্ডল", "Sun's mysterious outer atmosphere hotter than surface", "রহস্যজনকভাবে পৃষ্ঠের চেয়ে করোনার তাপমাত্রা লক্ষ গুণ বেশি!", Color(0xFFFFE082))
            ),
            fascinatingFactsBn = listOf(
                "সূর্যের ভেতরে প্রায় ১৩ লক্ষ পৃথিবী এঁটে যাবে!",
                "সূর্যের আলো পৃথিবীতে পৌঁছাতে সময় নেয় ৮ মিনিট ২০ সেকেন্ড।",
                "করোনাল ম্যাস ইজেকশন (CME) সৌরঝড় তৈরি করে যা পৃথিবীর মেরু অঞ্চলে নয়নাভিরাম অরোরা সৃষ্টি করে।"
            ),
            cosmicMysteriesBn = listOf(
                "করোনাল হিটিং প্যারাডক্স: সূর্যের পৃষ্ঠের তাপমাত্রা ৫,৫০০°C হলেও বাইরের করোনা অঞ্চল কেন লক্ষ লক্ষ ডিগ্রি সেলসিয়াস উত্তপ্ত?",
                "সোলার সাইকেল রহস্য: প্রতি ১১ বছর পর পর কেন সূর্যের উত্তর ও দক্ষিণ চৌম্বক মেরু নিজেদের স্থান অদলবদল করে?"
            ),
            missions = listOf(
                SpaceMission("2018", "Parker Solar Probe", "পার্কার সোলার প্রোব", "NASA", "Touched the Sun's corona at record speeds", "ইতিহাসের সবচেয়ে দ্রুততম মানবনির্মিত যান হিসেবে সূর্যের করোনা স্পর্শ করেছে"),
                SpaceMission("1995", "SOHO", "সোহো স্পেস অবজারভেটরি", "ESA / NASA", "Continuous solar wind monitor", "সূর্যের অভ্যন্তর ও সৌরঝড় সার্বক্ষণিক পর্যবেক্ষণ করছে")
            ),
            timeInfo = PlanetaryTimeInfo(
                yearInEarthDays = 84000000000.0,
                dayInHours = 600.0,
                yearSummaryEn = "230 Million Earth Years (Galactic Year)",
                yearSummaryBn = "২৩ কোটি পৃথিবী বছর (১ গ্যালাকটিক বছর)",
                daySummaryEn = "600 Hours (25 Earth Days)",
                daySummaryBn = "৬০০ ঘণ্টা (২৫ পৃথিবী দিন)",
                timeComparisonEn = "The last time the Sun was at this spot in the Milky Way, dinosaurs were first walking on Earth!",
                timeComparisonBn = "সূর্য যখন শেষবার মিল্কিওয়ের বর্তমান অবস্থানে ছিল, তখন পৃথিবীতে প্রথম ডায়নোসরদের পদচারণা শুরু হয়েছিল!",
                timeMysteryEn = "Differential Rotation: Because the Sun is a ball of plasma, its equator rotates in 25 days, but its poles take 35 days!",
                timeMysteryBn = "ডিফারেনশিয়াল ঘূর্ণন: সূর্য কোনো কঠিন গোলক নয়, তাই এর বিষুবরেখা ২৫ দিনে একবার ঘুরলেও মেরু অঞ্চল ঘুরতে ৩৫ দিন সময় নেয়!"
            )
        ),
        CelestialBody(
            id = "mercury",
            nameEn = "Mercury",
            nameBn = "বুধ",
            type = CelestialType.TERRESTRIAL,
            tagLineEn = "The swiftest and smallest terrestrial world near the Sun",
            tagLineBn = "সূর্যের সবচেয়ে নিকটের ক্ষিপ্র ও ক্ষুদ্রতম গ্রহ",
            baseRadiusDp = 9f,
            relativeScale = 0.55f,
            orbitDistanceUnits = 70f,
            orbitSpeedMultiplier = 4.15f,
            orbitInclinationDeg = 7.0f,
            axialTiltDeg = 0.03f,
            rotationSpeed = 0.2f,
            primaryColor = Color(0xFFB0BEC5),
            secondaryColor = Color(0xFF78909C),
            glowColor = Color(0xFFCFD8DC),
            diameterKm = "4,879 km",
            massKg = "3.3 × 10²³ kg",
            surfaceGravityMps2 = 3.7,
            dayLengthHours = "1,408 hours (58.6 Earth days)",
            yearLengthDays = "88 Earth days",
            averageTempC = "Day: 430°C, Night: -180°C",
            distanceFromSunMillionKm = "57.9 Million km",
            numberOfMoons = 0,
            atmosphereCompositionBn = listOf("অতি পাতলা এক্সোস্ফিয়ার: অক্সিজেন (৪২%)", "সোডিয়াম (২৯%)", "হাইড্রোজেন (২২%)"),
            coreLayers = listOf(
                CoreLayer("Solid Crust", "সিলিকা ভূত্বক", "১০০-৩০০ কিমি", "Silicate rock with wrinkles called lobate scarps", "সিলিকা পাথরে তৈরি, সংকোচনজনিত খাঁড়া খাঁদ রয়েছে", Color(0xFFBDBDBD)),
                CoreLayer("Silicate Mantle", "সিলিকা ম্যান্টল", "৬০০ কিমি", "Thin mantle surrounding an enormous metallic core", "তুলনামূলকভাবে পাতলা ম্যান্টল স্তর", Color(0xFF8D6E63)),
                CoreLayer("Giant Iron Core", "বিশাল লৌহ কেন্দ্র", "২,১০০ কিমি", "Occupies 85% of planet's total radius", "গ্রহটির ৮৫% অংশ জুড়েই রয়েছে বিশাল ধাতব লোহা-নিকেল কেন্দ্র!", Color(0xFF546E7A))
            ),
            fascinatingFactsBn = listOf(
                "বুধের কোনো বায়ুমণ্ডল না থাকায় আকাশ সব সময় কুচকুচে কালো থাকে।",
                "বুধের ছায়াযুক্ত গভীর মেরু গহ্বরে সূর্যের আলো না পৌঁছানোয় বরফের সন্ধান পাওয়া গেছে!",
                "গ্রহটি ধীরে ধীরে শীতল হয়ে সংকুচিত হচ্ছে, ফলে পৃষ্ঠে বিশালাকার ফাটল সৃষ্টি হয়েছে।"
            ),
            cosmicMysteriesBn = listOf(
                "বিশাল কেন্দ্র রহস্য: কেন বুধের ব্যাসার্ধের সিংহভাগ অংশই ধাতু দিয়ে গঠিত? প্রাক-সৌরজগতে কি কোনো মহাজাগতিক সংঘর্ষে এর বাইরের শিলাস্তর উড়ে গিয়েছিল?"
            ),
            missions = listOf(
                SpaceMission("2004", "MESSENGER", "মেসেঞ্জার মিশন", "NASA", "Orbited Mercury mapping its craters and ice", "বুধের বিস্তারিত ভূপ্রকৃতি ও মেরু বরফের মানচিত্র তৈরি করে"),
                SpaceMission("2018", "BepiColombo", "বেপিকলম্বো মিশন", "ESA / JAXA", "En route to unmask Mercury's magnetosphere", "২০২৬ সালে বুধের কক্ষপথে পৌঁছে বিশদ গবেষণা চালাবে")
            ),
            timeInfo = PlanetaryTimeInfo(
                yearInEarthDays = 87.97,
                dayInHours = 1407.6,
                yearSummaryEn = "88 Earth Days (0.24 Earth Years)",
                yearSummaryBn = "৮৮ পৃথিবী দিন (০.২৪ পৃথিবী বছর)",
                daySummaryEn = "1,408 Hours (58.6 Earth Days)",
                daySummaryBn = "১,৪০৮ ঘণ্টা (৫৮.৬ পৃথিবী দিন)",
                timeComparisonEn = "1 Earth Year = 4.15 Mercury Years! If you are 25 on Earth, you are 103.7 years old on Mercury!",
                timeComparisonBn = "পৃথিবীতে ১ বছর পার হলে বুধে ৪টিরও বেশি বছর পার হয়ে যায়! পৃথিবীতে বয়স ২৫ হলে বুধে আপনার বয়স ১০৩.৭ বছর!",
                timeMysteryEn = "Double Dawn Paradox: From sunrise to sunrise takes 176 Earth days—twice as long as its entire 88-day year!",
                timeMysteryBn = "বুধে ১টি সৌরদিন (এক সূর্যোদয় থেকে অন্য সূর্যোদয়) ১৭৬ পৃথিবী দিন লম্বা—অর্থাৎ এখানে ১টি দিন পার হতে ২টি পুরো বছর পার হয়ে যায়!"
            )
        ),
        CelestialBody(
            id = "venus",
            nameEn = "Venus",
            nameBn = "শুক্র",
            type = CelestialType.TERRESTRIAL,
            tagLineEn = "Shrouded in toxic sulfuric acid clouds and runaway greenhouse heat",
            tagLineBn = "ঘন সালফিউরিক মেঘ ও নরকসম উত্তাপে ঘেরা সন্ধ্যাতারা",
            baseRadiusDp = 13f,
            relativeScale = 0.95f,
            orbitDistanceUnits = 105f,
            orbitSpeedMultiplier = 1.62f,
            orbitInclinationDeg = 3.39f,
            axialTiltDeg = 177.3f,
            rotationSpeed = -0.15f,
            primaryColor = Color(0xFFFFD54F),
            secondaryColor = Color(0xFFFFB300),
            glowColor = Color(0xFFFFE082),
            atmosphereColor = Color(0x55FFE082),
            diameterKm = "12,104 km",
            massKg = "4.87 × 10²⁴ kg",
            surfaceGravityMps2 = 8.87,
            dayLengthHours = "5,832 hours (243 Earth days)",
            yearLengthDays = "225 Earth days",
            averageTempC = "465°C (Hottest in Solar System)",
            distanceFromSunMillionKm = "108.2 Million km",
            numberOfMoons = 0,
            atmosphereCompositionBn = listOf("কার্বন ডাই অক্সাইড (৯৬.৫%)", "নাইট্রোজেন (৩.৫%)", "সালফিউরিক এসিড মেঘ"),
            coreLayers = listOf(
                CoreLayer("Crust", "আগ্নেয়গিরির ভূত্বক", "৭০ কিমি", "Basaltic rock reshaped by thousands of volcanoes", "হাজার হাজার সক্রিয় আগ্নেয়গিরির লাভা দ্বারা গঠিত", Color(0xFFFFB74D)),
                CoreLayer("Rocky Mantle", "সিলিকা ম্যান্টল", "৩,০০০ কিমি", "Convecting solid rock layer", "উত্তপ্ত সান্দ্র শিলাস্তর", Color(0xFFD84315)),
                CoreLayer("Metallic Core", "তরল লোহা-নিকেল কেন্দ্র", "৩,২০০ কিমি", "Liquid metallic core without strong dynamo", "লোহা ও নিকেলের বিশাল তরল কেন্দ্র", Color(0xFFBF360C))
            ),
            fascinatingFactsBn = listOf(
                "শুক্রের দিন তার বছরের চেয়েও দীর্ঘ! নিজের অক্ষে একবার ঘুরতে সময় নেয় ২৪৩ দিন, অথচ সূর্য প্রদক্ষিণ করে ২২৫ দিনে।",
                "শুক্র ঘড়ির কাঁটার দিকে (পূর্ব থেকে পশ্চিমে) উল্টো ঘোরে!",
                "বায়ুর চাপ পৃথিবীর সমুদ্রপৃষ্ঠের চেয়ে ৯০ গুণ বেশি—যা সাগরের ৯০০ মিটার গভীরে থাকার মতো!"
            ),
            cosmicMysteriesBn = listOf(
                "ফসফিন গ্যাস ও অনুজীব রহস্য: ২০২০ সালে শুক্রের উচ্চ বায়ুমণ্ডলে ফসফিন গ্যাসের ট্রেস শনাক্ত হয়েছিল—মেঘের শীতল স্তরে কি কোনো আদিম ব্যাকটেরিয়াল জীবনের অস্তিত্ব সম্ভব?"
            ),
            missions = listOf(
                SpaceMission("1970", "Venera 7", "ভেনেরিয়া ৭", "Soviet Union", "First probe to land and transmit data from Venus", "ইতিহাসে প্রথমবার অন্য কোনো গ্রহের পৃষ্ঠে অবতরণ করে ডেটা পাঠায়"),
                SpaceMission("1989", "Magellan", "ম্যাগেলান মিশন", "NASA", "Radar mapped 98% of Venusian surface", "রাডারের মাধ্যমে শুক্রের ৯৮% পৃষ্ঠের মানচিত্র তৈরি করে")
            ),
            timeInfo = PlanetaryTimeInfo(
                yearInEarthDays = 224.7,
                dayInHours = 5832.5,
                yearSummaryEn = "225 Earth Days (0.62 Earth Years)",
                yearSummaryBn = "২২৫ পৃথিবী দিন (০.৬২ পৃথিবী বছর)",
                daySummaryEn = "5,832 Hours (243 Earth Days - Retrograde)",
                daySummaryBn = "৫,৮৩২ ঘণ্টা (২৪৩ পৃথিবী দিন - বিপরীতমুখী)",
                timeComparisonEn = "A day on Venus is longer than its year! The planet completes a full year before rotating once!",
                timeComparisonBn = "শুক্র গ্রহে ১ দিন তার ১ বছরের চেয়েও দীর্ঘ! অর্থাৎ অক্ষীয় ঘূর্ণন শেষ হওয়ার আগেই সূর্যের চারপাশের পুরো বছর শেষ হয়ে যায়!",
                timeMysteryEn = "Retrograde Rotation: Venus spins backwards, so the Sun rises in the west and sets in the east every 117 Earth days!",
                timeMysteryBn = "বিপরীত ঘড়ি: শুক্র উল্টো দিকে ঘোরে, তাই এখানে সূর্য পশ্চিম আকাশে উদিত হয় এবং পূর্ব আকাশে অস্ত যায় প্রতি ১১৭ পৃথিবী দিনে!"
            )
        ),
        CelestialBody(
            id = "earth",
            nameEn = "Earth",
            nameBn = "পৃথিবী",
            type = CelestialType.TERRESTRIAL,
            tagLineEn = "Our vibrant pale blue marble and oasis of life in the cosmic dark",
            tagLineBn = "মহাবিশ্বের একমাত্র জ্ঞাত প্রাণময় নীল মার্বেল",
            baseRadiusDp = 14f,
            relativeScale = 1.0f,
            orbitDistanceUnits = 145f,
            orbitSpeedMultiplier = 1.0f,
            orbitInclinationDeg = 0.0f,
            axialTiltDeg = 23.44f,
            rotationSpeed = 0.8f,
            primaryColor = Color(0xFF29B6F6),
            secondaryColor = Color(0xFF4CAF50),
            glowColor = Color(0xFF81D4FA),
            atmosphereColor = Color(0x660288D1),
            diameterKm = "12,742 km",
            massKg = "5.97 × 10²⁴ kg",
            surfaceGravityMps2 = 9.807,
            dayLengthHours = "23.93 hours (1 day)",
            yearLengthDays = "365.25 days",
            averageTempC = "15°C (-88°C to 58°C)",
            distanceFromSunMillionKm = "149.6 Million km (1 AU)",
            numberOfMoons = 1,
            atmosphereCompositionBn = listOf("নাইট্রোজেন (৭৮.১%)", "অক্সিজেন (২০.৯%)", "আর্গন (০.৯%)", "জলীয় বাষ্প ও কার্বন"),
            coreLayers = listOf(
                CoreLayer("Continental & Oceanic Crust", "ভূত্বক", "৫-৭০ কিমি", "Living tectonic plates floating on mantle", "টেকটোনিক প্লেট যা জীববৈচিত্র্যের ভারসাম্য রক্ষা করে", Color(0xFF4CAF50)),
                CoreLayer("Silicate Mantle", "ম্যাগমা ম্যান্টল", "২,৯০০ কিমি", "Semi-solid convective magma driving continental drift", "সান্দ্র ম্যাগমা যা মহাদেশীয় চলন নিয়ন্ত্রণ করে", Color(0xFFE65100)),
                CoreLayer("Outer Liquid Core", "বাইরের তরল কেন্দ্র", "২,২০০ কিমি", "Molten iron churning to create protective geomagnetic shield", "ঘূর্ণায়মান তরল লোহা যা পৃথিবীর চৌম্বক ঢাল তৈরি করে", Color(0xFFFF9800)),
                CoreLayer("Inner Solid Core", "অভ্যন্তরীণ কঠিন কেন্দ্র", "১,২২০ কিমি", "Solid crystalline iron-nickel sphere hot as the Sun", "সূর্যের পৃষ্ঠের সমান উত্তপ্ত কঠিন স্ফটিক লোহা-নিকেল বল", Color(0xFFFFF59D))
            ),
            fascinatingFactsBn = listOf(
                "পৃথিবীই সৌরজগতের একমাত্র গ্রহ যেখানে পানি কঠিন, তরল ও গ্যাসীয়—তিন রূপেই বিদ্যমান।",
                "পৃথিবীর চৌম্বক ক্ষেত্র ভ্যান অ্যালেন রেডিয়েশন বেল্ট তৈরি করে যা আমাদের সৌরঝড় থেকে রক্ষা করে।",
                "চাঁদ প্রতি বছর পৃথিবী থেকে প্রায় ৩.৮ সেন্টিমিটার করে দূরে সরে যাচ্ছে!"
            ),
            cosmicMysteriesBn = listOf(
                "প্রাণের উৎপত্তি রহস্য (Abiogenesis): জড় রাসায়নিক উপাদান থেকে কীভাবে ৪ বিলিয়ন বছর আগে পৃথিবীতে প্রথম স্ব-প্রতিলিপিকারী আরএনএ/ডিএনএ অণু গঠিত হয়েছিল?"
            ),
            missions = listOf(
                SpaceMission("1969", "Apollo 11", "অ্যাপোলো ১১", "NASA", "First humans walked on Earth's Moon", "মানুষের প্রথম চাঁদে অবতরণ ও 'বিশাল পদক্ষেপ'"),
                SpaceMission("1998", "ISS", "আন্তর্জাতিক মহাকাশ স্টেশন", "International", "Continuous human presence in low Earth orbit", "মহাকাশে একটানা মানব বসতি ও বৈজ্ঞানিক গবেষণাগার")
            ),
            timeInfo = PlanetaryTimeInfo(
                yearInEarthDays = 365.25,
                dayInHours = 23.934,
                yearSummaryEn = "365.25 Days (1 Earth Year)",
                yearSummaryBn = "৩৬৫.২৫ দিন (১ পৃথিবী বছর)",
                daySummaryEn = "23.93 Hours (24h Standard)",
                daySummaryBn = "২৩.৯৩ ঘণ্টা (১ দিন)",
                timeComparisonEn = "Universal standard reference for our calendar and time measurement.",
                timeComparisonBn = "মহাজাগতিক দিনপঞ্জি ও সময় পরিমাপের সার্বজনীন মানদণ্ড।",
                timeMysteryEn = "Leap Year & Milliseconds: Earth takes 365 days, 5 hours, 48 minutes, and 45 seconds to orbit the Sun, requiring a leap day every 4 years!",
                timeMysteryBn = "লিপ ইয়ার রহস্য: পৃথিবী সূর্যকে প্রদক্ষিণ করতে ৩৬৫ দিন ৫ ঘণ্টা ৪৮ মিনিট ৪৫ সেকেন্ড সময় নেওয়ায় প্রতি ৪ বছর পর ১টি লিপ ডে যুক্ত করতে হয়!"
            )
        ),
        CelestialBody(
            id = "mars",
            nameEn = "Mars",
            nameBn = "মঙ্গল",
            type = CelestialType.TERRESTRIAL,
            tagLineEn = "The dusty rust-red frontier and next destination for human exploration",
            tagLineBn = "ধূলিঝড়ে ঢাকা লাল গ্রহ—মানবজাতির পরবর্তী ভবিষ্যৎ গন্তব্য",
            baseRadiusDp = 10f,
            relativeScale = 0.68f,
            orbitDistanceUnits = 190f,
            orbitSpeedMultiplier = 0.53f,
            orbitInclinationDeg = 1.85f,
            axialTiltDeg = 25.19f,
            rotationSpeed = 0.78f,
            primaryColor = Color(0xFFFF5722),
            secondaryColor = Color(0xFFD84315),
            glowColor = Color(0xFFFF8A65),
            atmosphereColor = Color(0x33FF7043),
            diameterKm = "6,779 km",
            massKg = "6.42 × 10²³ kg",
            surfaceGravityMps2 = 3.72,
            dayLengthHours = "24.62 hours (1 Sol)",
            yearLengthDays = "687 Earth days (1.88 Earth years)",
            averageTempC = "-62°C (-140°C to 20°C)",
            distanceFromSunMillionKm = "227.9 Million km",
            numberOfMoons = 2,
            atmosphereCompositionBn = listOf("কার্বন ডাই অক্সাইড (৯৫.৩%)", "নাইট্রোজেন (২.৬%)", "আর্গন (১.৯%)"),
            coreLayers = listOf(
                CoreLayer("Iron Oxide Crust", "আয়রন অক্সাইড ভূত্বক", "৫০ কিমি", "Rusted dust covering volcanic plateaus and dry riverbeds", "মরচে ধরা আয়রন ডাস্টে আবৃত শিলাস্তর", Color(0xFFFF7043)),
                CoreLayer("Silicate Mantle", "সিলিকা ম্যান্টল", "১,৫০০ কিমি", "Extinct volcanic conduits beneath Olympus Mons", "অলিভাইন সমৃদ্ধ প্রাচীন ম্যান্টল স্তর", Color(0xFFBF360C)),
                CoreLayer("Metallic Core", "সালফারযুক্ত লোহা কেন্দ্র", "১,৮০০ কিমি", "Partially molten sulfur-rich iron core that lost its dynamo", "চৌম্বক ক্ষেত্র হারানো শীতলপ্রায় ধাতব কেন্দ্র", Color(0xFF4E342E))
            ),
            fascinatingFactsBn = listOf(
                "সৌরজগতের বৃহত্তম আগ্নেয়গিরি 'অলিম্পাস মনস' মঙ্গলে অবস্থিত—এটি এভারেস্টের চেয়ে ৩ গুণ উঁচু (২২ কিমি)! ",
                "মঙ্গলে রয়েছে ৪,০০০ কিমি দীর্ঘ গিরিখাত 'ভ্যালিস মেরিনারিস', যা পুরো উত্তর আমেরিকা মহাদেশের সমান দীর্ঘ!",
                "মঙ্গলের সূর্যাস্ত নীলচে রঙের দেখায় ধূলিকণার আলোর বিচ্ছুরণের কারণে।"
            ),
            cosmicMysteriesBn = listOf(
                "প্রাচীন সমুদ্রের পানি কোথায় গেল: ৩.৫ বিলিয়ন বছর আগে মঙ্গলে বহমান নদী ও সমুদ্র ছিল। বায়ুমণ্ডল হারিয়ে পানি কি বরফ হয়ে মাটির নিচে লুকিয়ে আছে?"
            ),
            missions = listOf(
                SpaceMission("2021", "Perseverance & Ingenuity", "পারসিভিয়ারেন্স ও ইনজেনুইটি", "NASA", "Jezero crater biosignature search and first powered flight", "জেজেরো ক্রেটারে প্রাচীন জীবাশ্ম সন্ধান ও প্রথম ভিনগ্রহী ড্রোন উড্ডয়ন"),
                SpaceMission("2014", "Mangalyaan (MOM)", "মঙ্গলযান", "ISRO", "First Asian nation to orbit Mars on debut attempt", "প্রথম প্রয়াসেই মঙ্গলের কক্ষপথে সফল প্রবেশ")
            ),
            timeInfo = PlanetaryTimeInfo(
                yearInEarthDays = 686.98,
                dayInHours = 24.623,
                yearSummaryEn = "687 Earth Days (1.88 Earth Years)",
                yearSummaryBn = "৬৮৭ পৃথিবী দিন (১.৮৮ পৃথিবী বছর)",
                daySummaryEn = "24.62 Hours (1 Sol = 24h 37m)",
                daySummaryBn = "২৪.৬২ ঘণ্টা (১ সল = ২৪ ঘণ্টা ৩৭ মিনিট)",
                timeComparisonEn = "A Martian day is only 39 minutes longer than Earth! But you age nearly twice as slowly in Mars years (a 30-year-old is 16 on Mars)!",
                timeComparisonBn = "মঙ্গলের ১ দিন পৃথিবীর চেয়ে মাত্র ৩৯ মিনিট বেশি! তবে মঙ্গলের হিসেবে বয়স প্রায় অর্ধেক (পৃথিবীতে বয়স ৩০ হলে মঙ্গলে মাত্র ১৬ বছর)!",
                timeMysteryEn = "Eccentric Seasons: Because of its elliptical orbit, Martian southern summers are 30 days shorter and much hotter than northern summers!",
                timeMysteryBn = "উপবৃত্তাকার ঋতু: মঙ্গলের কক্ষপথ বেশি উপবৃত্তাকার হওয়ায় এর দক্ষিণ গোলার্ধের গ্রীষ্মকাল ৩০ দিন সংক্ষিপ্ত কিন্তু অনেক বেশি উত্তপ্ত!"
            )
        ),
        CelestialBody(
            id = "jupiter",
            nameEn = "Jupiter",
            nameBn = "বৃহস্পতি",
            type = CelestialType.GAS_GIANT,
            tagLineEn = "The colossal gas king harboring 95 moons and centuries-old storms",
            tagLineBn = "সৌরজগতের গ্রহরাজ—প্রচণ্ড ঝড় আর ৯৫টি চাঁদের জগৎ",
            baseRadiusDp = 24f,
            relativeScale = 1.85f,
            orbitDistanceUnits = 250f,
            orbitSpeedMultiplier = 0.084f,
            orbitInclinationDeg = 1.3f,
            axialTiltDeg = 3.13f,
            rotationSpeed = 1.6f,
            primaryColor = Color(0xFFFFB74D),
            secondaryColor = Color(0xFFBcaaa4),
            glowColor = Color(0xFFFFCC80),
            atmosphereColor = Color(0x44FFA726),
            diameterKm = "139,820 km",
            massKg = "1.898 × 10²⁷ kg (2.5x all other planets combined)",
            surfaceGravityMps2 = 24.79,
            dayLengthHours = "9.93 hours (Fastest day in Solar System)",
            yearLengthDays = "4,333 Earth days (11.86 Earth years)",
            averageTempC = "-110°C",
            distanceFromSunMillionKm = "778.5 Million km",
            numberOfMoons = 95,
            atmosphereCompositionBn = listOf("হাইড্রোজেন (৮৯.৮%)", "হিলিয়াম (১০.২%)", "মিথেন ও অ্যামোনিয়া বরফ স্ফটিক"),
            coreLayers = listOf(
                CoreLayer("Cloud Bands", "বায়ুমণ্ডলীয় মেঘ বলয়", "১,০০০ কিমি", "Ammonia and water cloud storms rushing at 500 km/h", "বিপরীতমুখী ঘূর্ণনশীল অ্যামোনিয়া মেঘের স্তর", Color(0xFFFFCC80)),
                CoreLayer("Liquid Molecular Hydrogen", "তরল আণবিক হাইড্রোজেন", "২০,০০০ কিমি", "Dense fluid ocean of compressed hydrogen gas", "অসীম হাইড্রোজেন মহাসাগর", Color(0xFFFFB74D)),
                CoreLayer("Metallic Hydrogen Ocean", "ধাতব তরল হাইড্রোজেন", "৪০,০০০ কিমি", "Crushing pressure forces hydrogen into an electrical conductor", "তড়িৎ পরিবাহী তরল ধাতু যা শক্তিশালী ম্যাগনেটোস্ফিয়ার বানায়", Color(0xFFFF8A65)),
                CoreLayer("Diffuse Heavy Element Core", "ভারী উপাদান কেন্দ্র", "১০,০০০ কিমি", "Fuzzy rock, ice, and metal seed core dissolving in mantle", "পাথর, বরফ ও ধাতুর সান্দ্র কেন্দ্র", Color(0xFF5D4037))
            ),
            fascinatingFactsBn = listOf(
                "বৃহস্পতির বিখ্যাত 'গ্রেট রেড স্পট' ঝড়টি গত ৩৫০ বছরেরও বেশি সময় ধরে অনবরত জ্বলছে—যার ভেতরে একটি পুরো পৃথিবী অনায়াসেই এঁটে যাবে!",
                "বৃহস্পতির চাঁদ 'ইউরোপা'র বরফের চাদরের নিচে পৃথিবীর দ্বিগুণ পরিমাণ তরল পানির সমুদ্র রয়েছে!",
                "বৃহস্পতি একটি প্রাকৃতিক ঢাল হিসেবে কাজ করে—এর তীব্র মাধ্যাকর্ষণ অসংখ্য বিপজ্জনক ধূমকেতুকে নিজের দিকে টেনে এনে পৃথিবীকে মহাজাগতিক আঘাত থেকে বাঁচায়।"
            ),
            cosmicMysteriesBn = listOf(
                "ব্যর্থ নক্ষত্র কি?: বৃহস্পতি যদি জন্মের সময় প্রায় ৮০ গুণ বেশি ভর সঞ্চয় করত, তবে এটি একটি নক্ষত্রে পরিণত হতো এবং আমাদের সৌরজগতে দুটি সূর্য থাকত!"
            ),
            missions = listOf(
                SpaceMission("2016", "Juno", "জুনো মিশন", "NASA", "Peering beneath Jupiter's storms and magnetic dynamo", "বৃহস্পতির ঘন মেঘের নিচে উঁকি দিয়ে গভীর গঠন উন্মোচন করছে"),
                SpaceMission("2023", "JUICE", "জুস স্পেসক্রাফট", "ESA", "En route to explore icy ocean moons Ganymede, Callisto & Europa", "বৃহস্পতির বরফাবৃত চাঁদের মহাসাগরে জীবনের সম্ভাবনা পরীক্ষা করতে ছুটে চলেছে")
            ),
            timeInfo = PlanetaryTimeInfo(
                yearInEarthDays = 4332.59,
                dayInHours = 9.925,
                yearSummaryEn = "4,333 Earth Days (11.86 Earth Years)",
                yearSummaryBn = "৪,৩৩৩ পৃথিবী দিন (১১.৮৬ পৃথিবী বছর)",
                daySummaryEn = "9.93 Hours (Fastest in Solar System)",
                daySummaryBn = "৯.৯৩ ঘণ্টা (সৌরজগতের দ্রুততম দিন)",
                timeComparisonEn = "Jupiter spins so fast that a day and night passes in less than 10 hours! 1 Jupiter year takes nearly 12 Earth years.",
                timeComparisonBn = "বৃহস্পতি এত দ্রুত ঘোরে যে মাত্র ১০ ঘণ্টারও কম সময়ে একটি দিন-রাত শেষ হয়ে যায়! এখানে ১টি বছর পার হতে পৃথিবীর প্রায় ১২ বছর সময় লাগে।",
                timeMysteryEn = "Rapid Flattening: Its 9.9-hour spin makes Jupiter noticeably squashed, bulging at its equator by over 9,000 kilometers!",
                timeMysteryBn = "চরম ঘূর্ণন গতি: মাত্র ৯.৯ ঘণ্টার দ্রুত ঘূর্ণনের কারণে বৃহস্পতির বিষুবরেখা ৯,০০০ কিলোমিটারেরও বেশি ফুলে গিয়ে চ্যাপ্টা আকার ধারণ করেছে!"
            )
        ),
        CelestialBody(
            id = "saturn",
            nameEn = "Saturn",
            nameBn = "শনি",
            type = CelestialType.GAS_GIANT,
            tagLineEn = "The jewel of the solar system crowned with magnificent ice rings",
            tagLineBn = "সৌরজগতের সবচেয়ে মনোমুগ্ধকর বরফ বলয়ধারী গ্রহ",
            baseRadiusDp = 20f,
            relativeScale = 1.55f,
            orbitDistanceUnits = 310f,
            orbitSpeedMultiplier = 0.034f,
            orbitInclinationDeg = 2.48f,
            axialTiltDeg = 26.73f,
            rotationSpeed = 1.5f,
            primaryColor = Color(0xFFFFE082),
            secondaryColor = Color(0xFFD7CCC8),
            glowColor = Color(0xFFFFF9C4),
            ringConfig = RingConfig(
                innerRadiusFactor = 1.35f,
                outerRadiusFactor = 2.45f,
                ringColor = Color(0xCCF5E6B3),
                ringColorEdge = Color(0x44FFE082),
                tiltRad = 0.46f
            ),
            diameterKm = "116,460 km",
            massKg = "5.68 × 10²⁶ kg",
            surfaceGravityMps2 = 10.44,
            dayLengthHours = "10.7 hours",
            yearLengthDays = "10,759 Earth days (29.45 Earth years)",
            averageTempC = "-140°C",
            distanceFromSunMillionKm = "1,434 Million km",
            numberOfMoons = 146,
            atmosphereCompositionBn = listOf("হাইড্রোজেন (৯৬.৩%)", "হিলিয়াম (৩.২৫%)", "মিথেন ও অ্যামোনিয়া"),
            coreLayers = listOf(
                CoreLayer("Hazy Ammonia Atmosphere", "কুয়াশাচ্ছন্ন বায়ুমণ্ডল", "১,০০০ কিমি", "Golden haze concealing subtle cloud currents", "সোনারঙা অ্যামোনিয়ার মেঘের আচ্ছাদন", Color(0xFFFFF9C4)),
                CoreLayer("Liquid Hydrogen & Helium", "তরল হাইড্রোজেন ও হিলিয়াম", "৩০,০০০ কিমি", "Helium rain condensing through liquid layers", "হিলিয়ামের ফোঁটা বৃষ্টির মতো ঝরে পড়ে", Color(0xFFFFE082)),
                CoreLayer("Liquid Metallic Core", "ধাতব হাইড্রোজেন সাগর", "২০,০০০ কিমি", "Conductive layer maintaining magnetic field", "চৌম্বক ক্ষেত্র সৃষ্টিকারী ধাতব তরল", Color(0xFFFFCA28)),
                CoreLayer("Dense Rocky Core", "পাথুরে বরফ কেন্দ্র", "১০,০০০ কিমি", "Dense core of silicate and iron 15 times Earth's mass", "পৃথিবীর ভরের প্রায় ১৫ গুণ সমান ঘন কেন্দ্র", Color(0xFF8D6E63))
            ),
            fascinatingFactsBn = listOf(
                "শনির বলয় প্রায় ২,৮২,০০০ কিমি চওড়া হলেও এর পুরুত্ব গড়ে মাত্র ১০ মিটার থেকে ১ কিলোমিটার!",
                "শনি একমাত্র গ্রহ যার ঘনত্ব পানির চেয়েও কম (০.৬৮৭ গ্রাম/ঘন সেমি)—বিশাল কোনো মহাসাগরে রাখলে শনি ভেসে থাকবে!",
                "শনির চাঁদ 'এনসেলাডাস' তার দক্ষিণ মেরুর ফাটল থেকে মহাশূন্যে বরফ ও নোনা পানির বিশাল ফোয়ারা নিক্ষেপ করে।"
            ),
            cosmicMysteriesBn = listOf(
                "শনির বলয়ের বিলুপ্তি: বিজ্ঞানীদের হিসেবে মাধ্যাকর্ষণের টানে শনির বলয়ের বরফ 'রিং রেইন' হিসেবে গ্রহের বুকে ঝরে পড়ছে—আগামী ১০ থেকে ৩০ কোটি বছরের মধ্যে এই চোখজুড়ানো বলয় হয়তো আর থাকবেই না!"
            ),
            missions = listOf(
                SpaceMission("1997", "Cassini-Huygens", "ক্যাসিনি-হুইগেনস", "NASA / ESA", "Explored Saturn, rings and landed probe on moon Titan", "শনির বলয় ও চাঁদের বিস্তারিত রহস্য উন্মোচন করে এবং টাইটানে প্রব নামায়")
            ),
            timeInfo = PlanetaryTimeInfo(
                yearInEarthDays = 10759.22,
                dayInHours = 10.656,
                yearSummaryEn = "10,759 Earth Days (29.45 Earth Years)",
                yearSummaryBn = "১০,৭৫৯ পৃথিবী দিন (২৯.৪৫ পৃথিবী বছর)",
                daySummaryEn = "10.7 Hours",
                daySummaryBn = "১০.৭ ঘণ্টা",
                timeComparisonEn = "1 Saturn year takes nearly 30 Earth years! An 88-year-old on Earth has barely lived 3 years on Saturn!",
                timeComparisonBn = "শনির ১ বছর পৃথিবীর প্রায় ৩০ বছরের সমান! পৃথিবীতে ৮৮ বছর বাঁচলে শনির হিসেবে আপনার বয়স হবে মাত্র ৩ বছর!",
                timeMysteryEn = "Disappearing Ring Timeframe: Gravitational ring rain is pulling Saturn's rings into the planet at 10,000 kg/sec—they will completely vanish in 100M years!",
                timeMysteryBn = "বলয় বিলুপ্তির সময়রেখা: মাধ্যাকর্ষণের টানে প্রতি সেকেন্ডে ১০,০০০ কেজি বরফ বৃষ্টির মতো শনির বুকে ঝরে পড়ছে—আগামী ১০ কোটি বছরে বলয়টি সম্পূর্ণ নিঃশেষ হয়ে যাবে!"
            )
        ),
        CelestialBody(
            id = "uranus",
            nameEn = "Uranus",
            nameBn = "ইউরেনাস",
            type = CelestialType.ICE_GIANT,
            tagLineEn = "The tilted cyan ice giant rolling through space on its side",
            tagLineBn = "কাত হয়ে ঘুরতে থাকা দূরতম হিমশীতল সায়ান বরফ দানব",
            baseRadiusDp = 16f,
            relativeScale = 1.15f,
            orbitDistanceUnits = 365f,
            orbitSpeedMultiplier = 0.012f,
            orbitInclinationDeg = 0.77f,
            axialTiltDeg = 97.77f,
            rotationSpeed = -0.9f,
            primaryColor = Color(0xFF80DEEA),
            secondaryColor = Color(0xFF4DD0E1),
            glowColor = Color(0xFFB2EBF2),
            ringConfig = RingConfig(
                innerRadiusFactor = 1.25f,
                outerRadiusFactor = 1.7f,
                ringColor = Color(0x55B2EBF2),
                ringColorEdge = Color(0x2280DEEA),
                tiltRad = 1.7f
            ),
            diameterKm = "50,724 km",
            massKg = "8.68 × 10²⁵ kg",
            surfaceGravityMps2 = 8.69,
            dayLengthHours = "17.24 hours",
            yearLengthDays = "30,685 Earth days (84 Earth years)",
            averageTempC = "-195°C (Lowest: -224°C)",
            distanceFromSunMillionKm = "2,871 Million km",
            numberOfMoons = 28,
            atmosphereCompositionBn = listOf("হাইড্রোজেন (৮২.৫%)", "হিলিয়াম (১৫.২%)", "মিথেন (২.৩% যা এটিকে সায়ান নীল রঙ দেয়)"),
            coreLayers = listOf(
                CoreLayer("Methane-Rich Atmosphere", "মিথেন সমৃদ্ধ বায়ুমণ্ডল", "৫,০০০ কিমি", "Absorbs red light giving it a serene cyan glow", "লাল আলো শোষণ করে মনোমুগ্ধকর সায়ান রঙ তৈরি করে", Color(0xFFB2EBF2)),
                CoreLayer("Icy Mantle Ocean", "সুপারহিট আইসি ম্যান্টল", "১০,০০০ কিমি", "Slushy fluid water, ammonia, and methane ice", "পানি, অ্যামোনিয়া এবং মিথেনের ঘন পিচ্ছিল বরফ স্তর", Color(0xFF26C6DA)),
                CoreLayer("Rocky Silicate Core", "সিলিকা পাথুরে কেন্দ্র", "৫,০০০ কিমি", "Small rocky iron-nickel core", "পাথর ও লোহার কেন্দ্র", Color(0xFF00838F))
            ),
            fascinatingFactsBn = listOf(
                "ইউরেনাস তার অক্ষের ওপর প্রায় ৯৮ ডিগ্রি কাত হয়ে ঘোরে—অর্থাৎ এটি কক্ষপথে গড়িয়ে গড়িয়ে সূর্য প্রদক্ষিণ করে!",
                "এর অদ্ভুত কোণের কারণে এর উত্তর বা দক্ষিণ মেরুতে একটানা ৪২ বছর দিন এবং পরবর্তী ৪২ বছর ঘোর অন্ধকার রাত থাকে!",
                "ইউরেনাসের অভ্যন্তরীণ উত্তাপ অন্যান্য দানব গ্রহের চেয়ে অনেক কম, ফলে এর বায়ুমণ্ডল সৌরজগতের অন্যতম শীতলতম স্থান।"
            ),
            cosmicMysteriesBn = listOf(
                "মহাজাগতিক ধাক্কা: প্রাক-সৌরজগতে কোন প্রলয়ঙ্কর বস্তুর আঘাতেই কি ইউরেনাস পুরো কাত হয়ে শুয়ে পড়েছিল?"
            ),
            missions = listOf(
                SpaceMission("1986", "Voyager 2", "ভয়েজার ২", "NASA", "Only spacecraft to perform a close flyby of Uranus", "একমাত্র নভোযান হিসেবে ইউরেনাসের খুব কাছ দিয়ে উড়ে গিয়ে এর ছবি ও বলয়ের তথ্য সংগ্রহ করে")
            ),
            timeInfo = PlanetaryTimeInfo(
                yearInEarthDays = 30685.4,
                dayInHours = 17.24,
                yearSummaryEn = "30,685 Earth Days (84.01 Earth Years)",
                yearSummaryBn = "৩০,৬৮৫ পৃথিবী দিন (৮৪.০১ পৃথিবী বছর)",
                daySummaryEn = "17.24 Hours (Retrograde)",
                daySummaryBn = "১৭.২৪ ঘণ্টা (বিপরীতমুখী)",
                timeComparisonEn = "1 Uranus year equals a human lifetime (84 years)! Each pole spends 42 straight years in direct sunlight followed by 42 years of night!",
                timeComparisonBn = "ইউরেনাসের ১ বছর একজন মানুষের পূর্ণ আয়ুর সমান (৮৪ বছর)! এর প্রতিটি মেরুতে একটানা ৪২ বছর দিন এবং পরবর্তী ৪২ বছর ঘোর অন্ধকার রাত থাকে!",
                timeMysteryEn = "Rolling Sideways: Its 98° axial tilt means seasons last 21 Earth years each, with the sun rising straight up over the pole!",
                timeMysteryBn = "কাত হয়ে ঘূর্ণন: ৯৮° কাত হয়ে ঘোরার কারণে এর প্রতিটি ঋতু ২১ পৃথিবী বছর স্থায়ী হয় এবং সূর্য সরাসরি মেরুর ঠিক মাথার ওপর উদিত হয়!"
            )
        ),
        CelestialBody(
            id = "neptune",
            nameEn = "Neptune",
            nameBn = "নেপচুন",
            type = CelestialType.ICE_GIANT,
            tagLineEn = "The supersonic azure ice giant battling immense oceanic storms",
            tagLineBn = "তীব্রতম ঝড়ো হাওয়া ও গভীর গাঢ় নীল সাগরতুল্য গ্রহ",
            baseRadiusDp = 15f,
            relativeScale = 1.1f,
            orbitDistanceUnits = 420f,
            orbitSpeedMultiplier = 0.006f,
            orbitInclinationDeg = 1.77f,
            axialTiltDeg = 28.32f,
            rotationSpeed = 1.0f,
            primaryColor = Color(0xFF2979FF),
            secondaryColor = Color(0xFF1565C0),
            glowColor = Color(0xFF82B1FF),
            diameterKm = "49,244 km",
            massKg = "1.024 × 10²⁶ kg",
            surfaceGravityMps2 = 11.15,
            dayLengthHours = "16.1 hours",
            yearLengthDays = "60,190 Earth days (164.8 Earth years)",
            averageTempC = "-200°C",
            distanceFromSunMillionKm = "4,495 Million km (30 AU)",
            numberOfMoons = 16,
            atmosphereCompositionBn = listOf("হাইড্রোজেন (৮০%)", "হিলিয়াম (১৯%)", "মিথেন (১.৫%)"),
            coreLayers = listOf(
                CoreLayer("Upper Atmosphere", "উচ্চ বায়ুমণ্ডল", "৪,০০০ কিমি", "Cirrus-like frozen methane clouds and howling storms", "হিমায়িত মিথেনের মেঘ এবং তীব্র সাইক্লোন ঝড়", Color(0xFF82B1FF)),
                CoreLayer("Super-dense Icy Mantle", "ঘন বরফাবৃত ম্যান্টল", "১০,০০০ কিমি", "Ionic ocean where diamond rains may form under intense pressure", "প্রচণ্ড চাপে এখানে হীরার বৃষ্টি হতে পারে বলে ধারণা করা হয়!", Color(0xFF2979FF)),
                CoreLayer("Rock-Iron Core", "পাথুরে লৌহ কেন্দ্র", "৫,০০০ কিমি", "Earth-sized solid core of rock and metals", "পৃথিবীর সমান আকৃতির ধাতু ও শিলার কেন্দ্র", Color(0xFF0D47A1))
            ),
            fascinatingFactsBn = listOf(
                "নেপচুনে সৌরজগতের সবচেয়ে দ্রুতগতির বাতাস প্রবাহিত হয়—যার গতি ঘণ্টায় ২,১০০ কিলোমিটারেরও বেশি (শব্দের গতির চেয়েও বেশি)!",
                "নেপচুনের চাঁদ 'ট্রাইটন' সৌরজগতের একমাত্র বড় চাঁদ যা তার গ্রহের আবর্তনের উল্টো দিকে (রেট্রোগ্রেড) ঘোরে।",
                "১৮৪৬ সালে টেলিস্কোপে দেখার আগেই গণিতের হিসাবের মাধ্যমে নেপচুনের অস্তিত্ব প্রথম ভবিষ্যদ্বাণী করা হয়েছিল।"
            ),
            cosmicMysteriesBn = listOf(
                "হীরার বৃষ্টি (Diamond Rain): চরম চাপ ও উত্তাপের মিথেন ভেঙে কার্বন পরমাণুগুলো হীরায় রূপান্তরিত হয়ে কেন্দ্রস্থলের দিকে ঝরে পড়ে কি না, তা নিয়ে পদার্থবিজ্ঞানীদের গবেষণা চলছে।"
            ),
            missions = listOf(
                SpaceMission("1989", "Voyager 2", "ভয়েজার ২", "NASA", "Historic flyby discovering the Great Dark Spot and geysers on Triton", "নেপচুনের বিখ্যাত 'গ্রেট ডার্ক স্পট' এবং ট্রাইটনের নাইট্রোজেন গিজার আবিষ্কার করে")
            ),
            timeInfo = PlanetaryTimeInfo(
                yearInEarthDays = 60189.0,
                dayInHours = 16.11,
                yearSummaryEn = "60,190 Earth Days (164.79 Earth Years)",
                yearSummaryBn = "৬০,১৯০ পৃথিবী দিন (১৬৪.৭৯ পৃথিবী বছর)",
                daySummaryEn = "16.11 Hours",
                daySummaryBn = "১৬.১১ ঘণ্টা",
                timeComparisonEn = "Since its discovery in 1846, Neptune has only completed ONE single orbit around the Sun (celebrated in 2011)!",
                timeComparisonBn = "১৮৪৬ সালে আবিষ্কারের পর থেকে ২০১১ সাল পর্যন্ত মানুষের ইতিহাসে নেপচুন মাত্র ১টি পূর্ণ বছর সম্পন্ন করতে পেরেছে!",
                timeMysteryEn = "Diamond Rains in Real-Time: Interior pressures are so immense that methane breaks apart into solid diamond hailstones falling for millennia!",
                timeMysteryBn = "হীরার বৃষ্টি: চরম চাপে মিথেন ভেঙে কঠিন হীরার শিলাবৃষ্টিতে রূপান্তরিত হয়ে হাজার হাজার বছর ধরে কেন্দ্রের দিকে তলিয়ে যাচ্ছে!"
            )
        ),
        CelestialBody(
            id = "pluto",
            nameEn = "Pluto",
            nameBn = "প্লুটো",
            type = CelestialType.DWARF_PLANET,
            tagLineEn = "The mysterious nitrogen-ice sentinel guarding the Kuiper Belt",
            tagLineBn = "কাইপার বেল্টের বরফময় রহস্যময় বামন গ্রহ",
            baseRadiusDp = 7f,
            relativeScale = 0.4f,
            orbitDistanceUnits = 465f,
            orbitSpeedMultiplier = 0.004f,
            orbitInclinationDeg = 17.16f,
            axialTiltDeg = 122.5f,
            rotationSpeed = 0.2f,
            primaryColor = Color(0xFFD7CCC8),
            secondaryColor = Color(0xFFA1887F),
            glowColor = Color(0xFFEFEBE9),
            diameterKm = "2,376 km",
            massKg = "1.3 × 10²² kg",
            surfaceGravityMps2 = 0.62,
            dayLengthHours = "153.3 hours (6.4 Earth days)",
            yearLengthDays = "90,560 Earth days (248 Earth years)",
            averageTempC = "-232°C",
            distanceFromSunMillionKm = "5,906 Million km",
            numberOfMoons = 5,
            atmosphereCompositionBn = listOf("অতি পাতলা বায়ুমণ্ডল: নাইট্রোজেন (৯৯%)", "মিথেন", "কার্বন মনোক্সাইড"),
            coreLayers = listOf(
                CoreLayer("Nitrogen Ice Crust", "নাইট্রোজেন বরফের ভূত্বক", "১০০ কিমি", "Sputnik Planitia heart glacier flowing slowly", "বিখ্যাত হৃদয়-আকৃতির নাইট্রোজেন হিমবাহ", Color(0xFFEFEBE9)),
                CoreLayer("Subsurface Water Ocean?", "ভূগর্ভস্থ তরল পানির মহাসাগর?", "১০০-১৮০ কিমি", "Insulated ocean beneath the frozen crust", "বরফের নিচে তরল সমুদ্র থাকতে পারে", Color(0xFF90CAF9)),
                CoreLayer("Dense Silicate Core", "সিলিকা পাথুরে কেন্দ্র", "৮৫০ কিমি", "Dense core making up 70% of Pluto's mass", "প্লুটোর ভরের ৭০% দখল করে থাকা শিলাস্তর", Color(0xFF6D4C41))
            ),
            fascinatingFactsBn = listOf(
                "প্লুটোর পৃষ্ঠে একটি সুবিশাল হৃদয়-আকৃতির নাইট্রোজেন বরফের সমভূমি রয়েছে যার নাম 'টমবফ রেজিও'।",
                "প্লুটো ও তার বৃহত্তম চাঁদ 'শ্যারন' একে অপরের সাথে টাইডালি লকড—উভয়ই একে অপরের দিকে একই মুখ রেখে ঘোরে, যেন একটি বাইনারি নৃত্য!",
                "২০০৬ সালে আইএইউ (IAU) গ্রহের সংজ্ঞা পরিবর্তন করলে প্লুটোকে 'বামন গ্রহ' হিসেবে শ্রেণিবদ্ধ করা হয়।"
            ),
            cosmicMysteriesBn = listOf(
                "সক্রিয় ভূপ্রকৃতি রহস্য: সূর্য থেকে এত দূরে থাকা সত্ত্বেও প্লুটোর নাইট্রোজেন হিমবাহ কীভাবে এখনো চলাচল করে এবং এর পাহাড়গুলো কীভাবে সৃষ্টি হলো?"
            ),
            missions = listOf(
                SpaceMission("2015", "New Horizons", "নিউ হরাইজনস", "NASA", "Historic flyby unveiling Pluto's ice mountains and heart", "প্লুটোর চোখজুড়ানো উচ্চ রেজুলিউশনের ছবি ও বরফ পর্বতের বিস্ময়কর তথ্য পৃথিবীতে পাঠায়")
            ),
            timeInfo = PlanetaryTimeInfo(
                yearInEarthDays = 90560.0,
                dayInHours = 153.3,
                yearSummaryEn = "90,560 Earth Days (248.00 Earth Years)",
                yearSummaryBn = "৯০,৫৬০ পৃথিবী দিন (২৪৮.০০ পৃথিবী বছর)",
                daySummaryEn = "153.3 Hours (6.39 Earth Days)",
                daySummaryBn = "১৫৩.৩ ঘণ্টা (৬.৩৯ পৃথিবী দিন)",
                timeComparisonEn = "Pluto takes 248 Earth years to complete 1 orbit! Discovered in 1930, it won't finish its first orbit until 2178!",
                timeComparisonBn = "সূর্যকে ১ বার প্রদক্ষিণ করতে প্লুটোর ২৪৮ পৃথিবী বছর সময় লাগে! ১৯৩০ সালে আবিষ্কারের পর এর ১টি বছর পূর্ণ হবে ২১৭৮ সালে!",
                timeMysteryEn = "Tidally Locked Dance: Pluto and Charon take exactly 6.4 days to rotate on their axes and orbit each other, forever facing the same side!",
                timeMysteryBn = "চিরন্তন টাইডাল নৃত্য: প্লুটো এবং তার চাঁদ শ্যারন উভয়ই ৬.৪ দিনে নিজের অক্ষে ঘোরে এবং একে অপরকে প্রদক্ষিণ করে, ফলে আকাশে চাঁদ কখনো নড়ে না!"
            )
        )
    )

    fun getBodyById(id: String): CelestialBody {
        return celestialBodies.find { it.id.equals(id, ignoreCase = true) } ?: celestialBodies[0]
    }
}
