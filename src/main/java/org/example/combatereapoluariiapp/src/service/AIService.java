package org.example.combatereapoluariiapp.src.service;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.json.JSONObject;
import org.json.JSONArray;

public class AIService {
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent";
    // Here put your Gemini API kei in order for the AI to work
    private static final String API_KEY = "";

    private final Executor executor = Executors.newCachedThreadPool();

    public CompletableFuture<String> askQuestion(String question) {
        return CompletableFuture.supplyAsync(() -> {
            if (!isPollutionRelated(question)) {
                return "🤖 Sunt specializat în protecția mediului și sustenabilitate!\n\n" +
                        "Pot răspunde la întrebări despre:\n" +
                        "🌍 Schimbările climatice și emisiile CO2\n" +
                        "♻️ Reciclare și gestionarea deșeurilor\n" +
                        "⚡ Energie regenerabilă (solară, eoliană)\n" +
                        "💧 Protecția apei și a solului\n" +
                        "🚗 Transport ecologic și mobilitate verde\n" +
                        "🌱 Agricultura sustenabilă și permacultura\n" +
                        "🏠 Eficiență energetică în locuință\n\n" +
                        "Exemplu: 'Cum pot instala panouri solare acasă?'";
            }

            return getGeminiResponse(question);
        }, executor);
    }

    private boolean isPollutionRelated(String question) {
        String lowerQuestion = question.toLowerCase();
        String[] pollutionKeywords = {
                "poluare", "mediu", "carbon", "co2", "aer", "apă", "apa", "sol", "deșeu", "deseuri",
                "reciclare", "energie", "sustenabil", "ecologic", "verde", "pădure", "padure",
                "natură", "natura", "climat", "climatic", "emisii", "toxic", "plastic", "gunoi",
                "poluant", "biodegradabil", "organic", "compost", "solar", "vânt", "vant",
                "hidro", "eolian", "nuclear", "fossil", "gaze", "petrol", "încălzire", "incalzire",
                "sera", "ozon", "smog", "conservare", "protecție", "protectie", "durabil",
                "regenerabil", "fotovoltaic", "termic", "biomasa", "eficiență", "eficienta",
                "izolație", "izolatie", "LED", "economie", "circular", "eco-friendly",
                "biodiversitate", "ecosistem", "habitat", "specii", "plantație", "plantatie",
                "defrișare", "defrisare", "purificare", "curățare", "curatare", "filtru",

                "reduc", "economisesc", "salvez", "protejez", "conserv", "recicllez", "compostez",
                "plantez", "ameliorez", "îmbunătățesc", "imbunatatesc", "optimizez", "minimizez",

                "pollution", "environment", "climate", "waste", "recycling", "renewable",
                "sustainable", "ecology", "green", "emissions", "toxic", "biodegradable"
        };

        return java.util.Arrays.stream(pollutionKeywords)
                .anyMatch(lowerQuestion::contains);
    }

    private String getGeminiResponse(String question) {
        try {
            System.out.println("🤖 Asking Google Gemini: " + question);
            String environmentalPrompt = createRomanianEnvironmentalPrompt(question);

            String fullUrl = API_URL + "?key=" + API_KEY;
            URL url = new URL(fullUrl);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JSONObject requestBody = new JSONObject();
            JSONArray contents = new JSONArray();
            JSONObject content = new JSONObject();
            JSONArray parts = new JSONArray();
            JSONObject part = new JSONObject();

            part.put("text", environmentalPrompt);
            parts.put(part);
            content.put("parts", parts);
            contents.put(content);
            requestBody.put("contents", contents);

            JSONObject generationConfig = new JSONObject();
            generationConfig.put("temperature", 0.7);
            generationConfig.put("topK", 40);
            generationConfig.put("topP", 0.95);
            generationConfig.put("maxOutputTokens", 1024);
            requestBody.put("generationConfig", generationConfig);

            JSONArray safetySettings = new JSONArray();
            String[] categories = {"HARM_CATEGORY_HARASSMENT", "HARM_CATEGORY_HATE_SPEECH",
                    "HARM_CATEGORY_SEXUALLY_EXPLICIT", "HARM_CATEGORY_DANGEROUS_CONTENT"};
            for (String category : categories) {
                JSONObject safetySetting = new JSONObject();
                safetySetting.put("category", category);
                safetySetting.put("threshold", "BLOCK_MEDIUM_AND_ABOVE");
                safetySettings.put(safetySetting);
            }
            requestBody.put("safetySettings", safetySettings);

            try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
                writer.write(requestBody.toString());
                writer.flush();
            }

            int responseCode = connection.getResponseCode();
            System.out.println("✅ Gemini Response code: " + responseCode);

            if (responseCode == 200) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    String jsonResponse = response.toString();
                    System.out.println("📝 Raw response preview: " + jsonResponse.substring(0, Math.min(150, jsonResponse.length())) + "...");

                    return parseGeminiResponse(jsonResponse, question);
                }
            } else {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder error = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        error.append(line);
                    }
                    System.err.println("❌ Gemini API Error (" + responseCode + "): " + error.toString());
                }
                return getFallbackEnvironmentalResponse(question);
            }

        } catch (Exception e) {
            System.err.println("❌ Exception calling Gemini: " + e.getMessage());
            e.printStackTrace();
            return getFallbackEnvironmentalResponse(question);
        }
    }

    private String parseGeminiResponse(String jsonResponse, String originalQuestion) {
        try {
            JSONObject response = new JSONObject(jsonResponse);
            JSONArray candidates = response.getJSONArray("candidates");

            if (candidates.length() > 0) {
                JSONObject candidate = candidates.getJSONObject(0);
                JSONObject content = candidate.getJSONObject("content");
                JSONArray parts = content.getJSONArray("parts");

                if (parts.length() > 0) {
                    String generatedText = parts.getJSONObject(0).getString("text");
                    System.out.println("✅ Successfully parsed Gemini response");
                    return enhanceGeminiResponse(generatedText, originalQuestion);
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error parsing Gemini response: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("⚠️ Falling back to built-in knowledge");
        return getFallbackEnvironmentalResponse(originalQuestion);
    }

    private String enhanceGeminiResponse(String geminiResponse, String question) {
        StringBuilder enhanced = new StringBuilder();

        enhanced.append("🤖 ").append(geminiResponse.trim());

        String lowerQuestion = question.toLowerCase();

        if (lowerQuestion.contains("acasă") || lowerQuestion.contains("casa") || lowerQuestion.contains("apartament")) {
            enhanced.append("\n\n💡 Sfaturi suplimentare pentru acasă în România:\n");
            enhanced.append("• Programul Rabla pentru Electrocasnice - până la 400 lei reducere\n");
            enhanced.append("• Casa Verde Fotovoltaice - finanțare pentru panouri solare\n");
            enhanced.append("• Verifică eticheta energetică la cumpărarea aparatelor\n");
        }

        if (lowerQuestion.contains("energie") || lowerQuestion.contains("solar") || lowerQuestion.contains("eolian")) {
            enhanced.append("\n\n⚡ Context energetic România:\n");
            enhanced.append("• România: 210+ zile însorite anual - potențial solar excelent\n");
            enhanced.append("• Casa Verde Fotovoltaice: până la 20.000 lei finanțare\n");
            enhanced.append("• Dobrogea: cel mai mare potențial eolian din țară\n");
        }

        if (lowerQuestion.contains("deșeu") || lowerQuestion.contains("reciclare") || lowerQuestion.contains("gunoi")) {
            enhanced.append("\n\n♻️ Reciclare România:\n");
            enhanced.append("• România: doar 13% reciclare vs 48% media UE\n");
            enhanced.append("• Colectare selectivă obligatorie în toate orașele\n");
            enhanced.append("• Eco-puncte pentru deșeuri speciale în cartiere\n");
        }

        if (lowerQuestion.contains("transport") || lowerQuestion.contains("mașin") || lowerQuestion.contains("electric")) {
            enhanced.append("\n\n🚗 Transport verde România:\n");
            enhanced.append("• Rabla Plus 2024: până la 10.000€ pentru vehicule electrice\n");
            enhanced.append("• 800+ stații de încărcare publice\n");
            enhanced.append("• Scutire taxe pentru vehiculele electrice\n");
        }

        if (lowerQuestion.contains("românia") || lowerQuestion.contains("romania") || lowerQuestion.contains("român")) {
            enhanced.append("\n\n🇷🇴 Context specific României:\n");
            enhanced.append("• România: locul 3 în UE la resurse de apă dulce\n");
            enhanced.append("• 28% suprafață împădurită (obiectiv: 30% până în 2030)\n");
            enhanced.append("• Carpații: cel mai mare ecosistem forestier intact din UE\n");
        }

        enhanced.append("\n\n🌱 Pentru mai multe informații detaliate, explorează secțiunea 'Soluții' și 'Articole' din aplicație!");

        return enhanced.toString();
    }

    private String getFallbackEnvironmentalResponse(String question) {
        String lowerQuestion = question.toLowerCase();

        if (lowerQuestion.contains("energie") || lowerQuestion.contains("solar") || lowerQuestion.contains("eolian")) {
            return "⚡ ENERGIA REGENERABILĂ ÎN ROMÂNIA\n\n" +
                    "🌞 Energia solară:\n" +
                    "• România are 210 zile însorite pe an în medie\n" +
                    "• Programul Casa Verde Fotovoltaice oferă până la 20.000 lei\n" +
                    "• Un sistem de 3kW produce ~4.500 kWh anual\n" +
                    "• Amortizarea: 6-8 ani, garanție 25 ani\n\n" +
                    "💨 Energia eoliană:\n" +
                    "• Dobrogea are cel mai mare potențial eolian\n" +
                    "• România: locul 12 în UE la capacitatea eoliană\n" +
                    "• O turbină produce energie pentru 1.500 gospodării\n\n" +
                    "🏠 Eficiență energetică:\n" +
                    "• Programul Anghel Saligny pentru termoizolarea blocurilor\n" +
                    "• LED-urile reduc consumul cu 80% față de becurile clasice\n" +
                    "• Certificatul energetic este obligatoriu la vânzare/închiriere";

        } else if (lowerQuestion.contains("deșeu") || lowerQuestion.contains("reciclare") || lowerQuestion.contains("gunoi")) {
            return "♻️ GESTIONAREA DEȘEURILOR ÎN ROMÂNIA\n\n" +
                    "📊 Situația actuală:\n" +
                    "• România reciclează doar 13% din deșeuri (vs 48% media UE)\n" +
                    "• Fiecare român produce 280 kg deșeuri/an\n" +
                    "• Obiectiv UE 2025: 55% reciclare\n\n" +
                    "🗂️ Colectare selectivă:\n" +
                    "• Galben: plastic și metal\n" +
                    "• Albastru: hârtie și carton\n" +
                    "• Verde: sticlă\n" +
                    "• Maro: biodegradabile\n\n" +
                    "💰 Beneficii economice:\n" +
                    "• 1 tonă hârtie reciclată = salvează 17 copaci\n" +
                    "• 1 tonă plastic reciclat = economisește 2.000 litri petrol\n" +
                    "• Compostarea reduce deșeurile cu 30-40%\n\n" +
                    "🏆 Programe românești:\n" +
                    "• Rabla pentru Electrocasnice - schimbul aparatelor vechi\n" +
                    "• Colectarea DEEE - electronice gratuit la magazine\n" +
                    "• Eco-Punct în cartiere pentru deșeuri speciale";

        } else if (lowerQuestion.contains("transport") || lowerQuestion.contains("mașin") || lowerQuestion.contains("autobuz")) {
            return "🚗 TRANSPORT SUSTENABIL ÎN ROMÂNIA\n\n" +
                    "🚌 Transport public:\n" +
                    "• București: 100 autobuze electrice până în 2025\n" +
                    "• Cluj-Napoca: primul tramvai pe baterii din România\n" +
                    "• Brașov introduce autobuze pe hidrogen\n\n" +
                    "⚡ Vehicule electrice:\n" +
                    "• Programul Rabla Plus: până la 10.000€ pentru electric\n" +
                    "• România: 800+ stații de încărcare publice\n" +
                    "• Scutire de taxa auto pentru vehiculele electrice\n\n" +
                    "🚲 Mobilitate alternativă:\n" +
                    "• Piste de biciclete: București 100km, Cluj 50km\n" +
                    "• Bike-sharing în 15+ orașe românești\n" +
                    "• Trotinete electrice - 50% mai puțin CO2 vs mașina";

        } else if (lowerQuestion.contains("apă") || lowerQuestion.contains("apa") || lowerQuestion.contains("râu")) {
            return "💧 PROTECȚIA APEI ÎN ROMÂNIA\n\n" +
                    "🌊 Resursele de apă:\n" +
                    "• România: locul 3 în UE la resurse de apă dulce\n" +
                    "• Dunărea: 1.075 km prin România\n" +
                    "• 12 bazine hidrografice majore\n\n" +
                    "⚠️ Provocări actuale:\n" +
                    "• 40% din râuri sunt poluate chimic\n" +
                    "• Seceta afectează 60% din teritoriu vara\n" +
                    "• Nitrații din agricultura intensivă poluează pânza freatică\n\n" +
                    "💡 Soluții practice:\n" +
                    "• Sisteme de colectare apă de ploaie (600L/m²/an)\n" +
                    "• Detergenți fără fosfați (obligatoriu în UE)\n" +
                    "• Economizoare de apă reduc consumul cu 30%\n" +
                    "• Plantare vegetație pe maluri - filtru natural";

        } else if (lowerQuestion.contains("pădure") || lowerQuestion.contains("copac") || lowerQuestion.contains("plantare")) {
            return "🌳 PĂDURILE ȘI ÎMPĂDURIREA ÎN ROMÂNIA\n\n" +
                    "🏞️ Situația pădurilor:\n" +
                    "• România: 28% suprafață împădurită (vs 38% media UE)\n" +
                    "• Carpații: cel mai mare ecosistem forestier intact din UE\n" +
                    "• 40.000 ha defrișate anual (legal și ilegal)\n\n" +
                    "🌱 Programe de împădurire:\n" +
                    "• '50 mil. copaci în 5 ani' - program național\n" +
                    "• 'Adoptă o pădure' - Romsilva\n" +
                    "• Ziua Pădurii (15 martie) - plantări în toată țara\n\n" +
                    "💚 Beneficii măsurabile:\n" +
                    "• 1 copac absorb 22kg CO2/an\n" +
                    "• 1 hectar pădure produce oxigen pentru 20 persoane\n" +
                    "• Pădurile reduc temperatura cu 2-8°C în orașe";
        }

        return "🌍 PROTECȚIA MEDIULUI ÎN ROMÂNIA\n\n" +
                "Mulțumesc pentru întrebarea ta despre mediu! Iată câteva informații generale:\n\n" +
                "📈 Progresul României:\n" +
                "• Reducere 60% emisii GES față de 1989\n" +
                "• 24% energie din surse regenerabile (2023)\n" +
                "• Obiectiv UE 2030: 32% energie verde\n\n" +
                "🎯 Provocări majore:\n" +
                "• Calitatea aerului în orașele mari\n" +
                "• Gestionarea deșeurilor urbane\n" +
                "• Protecția biodiversității\n\n" +
                "💡 Ce poți face:\n" +
                "• Alege produse locale și de sezon\n" +
                "• Participă la inițiative de voluntariat ecologic\n" +
                "• Informează-te despre programele guvernamentale\n" +
                "• Fii un exemplu în comunitatea ta\n\n" +
                "Pentru sfaturi specifice, întreabă despre energie, deșeuri, transport sau apă!";
    }

    private String createRomanianEnvironmentalPrompt(String question) {
        return "Ești un expert român în protecția mediului și sustenabilitate, cu cunoștințe detaliate despre situația din România. " +
                "Răspunde în română la următoarea întrebare, oferind sfaturi practice, " +
                "științifice și relevante pentru România. Incluzi date concrete, " +
                "programe guvernamentale românești relevante, și soluții implementabile:\n\n" +
                "Întrebare: " + question + "\n\n" +
                "Răspunsul să fie structurat, practic și să includă exemple concrete pentru România. " +
                "Concentrează-te pe acțiuni măsurabile și beneficii cuantificate. " +
                "Oferă informații actuale și utile pentru cetățenii români.";
    }
}