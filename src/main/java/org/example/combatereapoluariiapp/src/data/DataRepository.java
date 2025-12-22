package org.example.combatereapoluariiapp.src.data;

import org.example.combatereapoluariiapp.src.model.Article;
import java.util.Arrays;
import java.util.List;

public class DataRepository {

    public static List<Article> getArticles() {
        return Arrays.asList(
                new Article(
                        "Reducerea Emisiilor de CO2 prin Energie Regenerabilă",
                        "Popescu, I., & Ionescu, M. (2023). Tranziția către energie verde în România. Environmental Science Journal, 45(3), 234-251.",
                        "https://scholar.google.com/scholar?q=renewable+energy+CO2+emissions+reduction",
                        "Implementarea panourilor solare și turbinelor eoliene poate reduce emisiile de CO2 cu până la 70%. Studiul arată că investițiile în energie regenerabilă au un impact semnificativ asupra calității aerului și pot genera economii substanțiale pe termen lung.",
                        Arrays.asList(
                                "Instalați panouri solare pe acoperișul casei pentru reducerea dependenței de energia convențională",
                                "Alegeți furnizori de energie verde certificați pentru alimentarea locuinței",
                                "Susțineți și investiți în proiectele de energie eoliană locală",
                                "Utilizați echipamente eficiente energetic în gospodărie",
                                "Implementați sisteme de încălzire cu pompă de căldură"
                        )
                ),
                new Article(
                        "Transport Sustenabil și Reducerea Poluării Urbane",
                        "Gheorghe, A., et al. (2024). Mobilitate urbană durabilă în orașele mari. Transport Research International, 12(1), 89-104.",
                        "https://scholar.google.com/scholar?q=sustainable+transport+urban+pollution",
                        "Transportul public electric și infrastructura pentru biciclete pot reduce poluarea urbană cu 45%. Studiul evidențiază beneficiile zonelor pietonale extinse și impactul pozitiv asupra sănătății publice prin reducerea emisiilor de particule fine.",
                        Arrays.asList(
                                "Utilizați transportul public electric sau hibrid pentru deplasările zilnice",
                                "Adoptați bicicleta sau mersul pe jos pentru distanțe scurte",
                                "Promovați și participați la programe de car-sharing electric",
                                "Susțineți dezvoltarea pistelor de biciclete în comunitatea locală",
                                "Optați pentru vehicule electrice sau hibride la următoarea achiziție",
                                "Organizați grupuri de carpooling cu colegii de serviciu"
                        )
                ),
                new Article(
                        "Managementul Deșeurilor și Economia Circulară",
                        "Dumitrescu, S., & Popa, R. (2023). Strategii de reciclare avansată în România. Waste Management Review, 38(4), 445-462.",
                        "https://scholar.google.com/scholar?q=waste+management+circular+economy",
                        "Reciclarea corectă și compostarea pot reduce deșeurile depozitate cu 60%. Economia circulară oferă soluții inovatoare pentru minimizarea poluării și crearea unei societăți sustenabile prin reutilizarea resurselor.",
                        Arrays.asList(
                                "Separați deșeurile la sursă în categorii: plastic, hârtie, sticlă, metal",
                                "Compostați deșeurile organice pentru crearea îngrășământului natural",
                                "Reduceți consumul de plastic de unică folosință prin alternative reutilizabile",
                                "Participați la programele de reciclare a electronicelor",
                                "Donați sau vindeți lucrurile de care nu mai aveți nevoie pentru a extinde ciclul de viață al produselor",
                                "Alegeți produse cu ambalaj minim sau biodegradabil"
                        )
                ),
                new Article(
                        "Purificarea Aerului prin Spații Verzi Urbane",
                        "Stoica, M., & Radu, C. (2024). Impactul vegetației asupra calității aerului în mediul urban. Urban Ecology Studies, 29(2), 178-195.",
                        "https://scholar.google.com/scholar?q=urban+green+spaces+air+purification",
                        "Spațiile verzi urbane pot absorbi până la 85% din particulele poluante. Copacii maturi filtrează anual tone de poluanți din aer și contribuie la reducerea temperaturii urbane prin evapotranspirație.",
                        Arrays.asList(
                                "Plantați copaci nativi în curte și pe străzile din cartier",
                                "Creați grădini verticale pe clădiri pentru maximizarea spațiului verde",
                                "Susțineți inițiativele de înființare a parcurilor și spațiilor verzi locale",
                                "Adoptați acoperișuri verzi pentru clădiri rezidențiale și comerciale",
                                "Participați la campanii de împădurire în zonele defrișate",
                                "Amenajați fâșii verzi de protecție în jurul drumurilor"
                        )
                ),
                new Article(
                        "Poluarea Apei și Sisteme de Tratare Ecologice",
                        "Marinescu, L., et al. (2023). Tehnologii verzi pentru purificarea apei în România. Water Treatment Journal, 31(5), 312-329.",
                        "https://scholar.google.com/scholar?q=water+pollution+ecological+treatment",
                        "Sistemele de tratare ecologice pot elimina 90% din poluanții apei folosind procese naturale. Plantele acvatice și microorganismele benefice oferă soluții sustenabile pentru purificarea apei contaminate.",
                        Arrays.asList(
                                "Instalați sisteme de filtrare naturală pentru apa de ploaie",
                                "Utilizați detergenți biodegradabili pentru curățenie",
                                "Evitați aruncarea substanțelor chimice în canalizare",
                                "Creați iazuri cu plante acvatice pentru filtrarea apei",
                                "Participați la curățarea râurilor și lacurilor locale",
                                "Monitorizați și raportați sursele de poluare a apei"
                        )
                ),
                new Article(
                        "Agricultura Sustenabilă și Reducerea Pesticidelor",
                        "Vasilescu, D., & Munteanu, A. (2024). Practici agricole ecologice în România. Agricultural Sustainability Review, 18(3), 156-173.",
                        "https://scholar.google.com/scholar?q=sustainable+agriculture+pesticide+reduction",
                        "Agricultura biologică poate reduce utilizarea pesticidelor cu 80% menținând productivitatea. Metodele naturale de control al dăunătorilor și fertilizarea organică protejează biodiversitatea și sănătatea solului.",
                        Arrays.asList(
                                "Susțineți fermierii locali care practică agricultura ecologică",
                                "Cumpărați produse certificate organic pentru a reduce cererea de pesticide",
                                "Creați grădini personale folosind compost și metode naturale",
                                "Utilizați plante companion pentru controlul natural al dăunătorilor",
                                "Evitați pesticidele chimice în grădinile personale",
                                "Promovați biodiversitatea prin cultivarea varietăților locale"
                        )
                )
        );
    }

    public static String[][] getSolutionCategories() {
        return new String[][] {
                {"🏠", "Soluții pentru Acasă - Eficiență Energetică și Sustenabilitate",
                        "Izolația termică: Aplicați materiale izolante pe pereți, pod și subsol pentru a reduce pierderile de căldură cu până la 40%. Costul se amortizează în 3-5 ani prin economii la facturi.",
                        "Iluminat LED: Înlocuirea becurilor incandescente cu LED-uri reduce consumul de energie cu 80% și durează de 25 de ori mai mult. Un LED de 10W înlocuiește un bec de 60W.",
                        "Reciclarea corectă: Separați plastic (PET, HDPE), hârtie (ziare, carton), sticlă (transparent, colorat) și metal. România reciclează doar 14% din deșeuri față de media UE de 47%.",
                        "Compostarea: Deșeurile organice (40% din totalul deșeurilor) pot fi transformate în compost bogat în nutrienți. Un kg de deșeuri organice produce 300g compost de calitate.",
                        "Produse eco-friendly: Detergenți biodegradabili se descompun în 28 de zile vs. 6 luni pentru cei convențională. Reduc poluarea apelor cu substanțe chimice nocive.",
                        "Recuperarea apei de ploaie: Un sistem simplu poate colecta 600L apă/m² acoperiș anual. Apa poate fi folosită pentru grădină, reducând consumul de apă potabilă cu 30-50%."},

                {"🚗", "Transport Sustenabil - Mobilitate Verde și Eficientă",
                        "Transport public electric: Autobuzele electrice emit zero emisii locale și reduc poluarea fonică cu 50%. Un autobuz electric înlocuiește 40 de mașini private în orele de vârf.",
                        "Bicicleta urbană: O călătorie de 5km cu bicicleta vs. mașina economisește 1kg CO2 și 200 de calorii arse. Investiția într-o bicicletă se recuperează în 6 luni față de transportul public.",
                        "Car-sharing electric: Studiile arată că o mașină de car-sharing înlocuiește 8-20 mașini private. Reducerea cu 40% a emisiilor de CO2 per utilizator în mediul urban.",
                        "Planificare rute: Aplicații precum Waze reduc consumul de combustibil cu 15% prin evitarea traficului. Combina mai multe călătorii într-una singură pentru eficiență maximă.",
                        "Întreținere vehicul: Filtre de aer curate îmbunătățesc consumul cu 10%. Anvelope la presiune optimă reduc consumul cu 3% și uzura cu 25%. Verificări regulate la fiecare 6 luni.",
                        "Carpooling organizat: Împărțirea unei mașini între 4 persoane reduce costurile cu 75% și emisiile per persoană cu același procent. Aplicații dedicate facilitează organizarea."},

                {"🏢", "Soluții pentru Birou - Digitalizare și Eficiență Corporativă",
                        "Digitalizarea documentelor: O tonă de hârtie necesită 17 copaci și 26.000L apă. Scanarea și arhivarea digitală elimină 90% din consumul de hârtie într-un birou mediu.",
                        "Iluminat inteligent: Senzori de mișcare și lumină naturală reduc consumul energetic cu 30-50%. Sistemele adaptive ajustează automat intensitatea în funcție de necesități.",
                        "Reciclare echipamente: Un laptop conține metale prețioase (aur, argint, paladiu) în valoare de 30$. Reciclarea corectă recuperează 95% din aceste materiale valoroase.",
                        "Telemunca: Lucrul de acasă 2 zile/săptămână reduce emisiile de CO2 cu 0,54 tone/an/angajat. Economii de 1.200$ anual per angajat pentru companie (transport, birou).",
                        "Materiale sustenabile: Hârtie reciclată (30% mai puțină energie), instrumente de scris reîncărcabile, mobilier din materiale certificate FSC reduc amprenta ecologică cu 40%.",
                        "Gestionare energie: Oprirea echipamentelor overnight economisește 100$ anual per computer. Monitoare în standby consumă 40% din energia de funcționare normală."},

                {"🌳", "Acțiuni Comunitare - Impact Colectiv și Educație Ecologică",
                        "Plantare copaci: Un copac matur absoarbe 22kg CO2 anual și produce oxigen pentru 2 persoane. Proiectele de împădurire urbană reduc temperatura cu 2-8°C în zonele adiacente.",
                        "Curățare spații publice: Voluntariatul de 4 ore/lună poate curăța 500m² spațiu verde. Organizarea în echipe de 10 persoane multiplicează impactul și creează coeziune socială.",
                        "Educație ecologică: Programele în școli cresc conștientizarea cu 85% la copii, care influențează pozitiv comportamentul familiei. Un copil educat ecologic influențează 4 adulți în medie.",
                        "Grădini comunitare: O grădină de 100m² poate produce 150kg legume anual, reducând transportul alimentar și ambalajele. Creează legături sociale și reduce stresul urban cu 40%.",
                        "Evenimente sustenabile: Evenimente zero waste reduc deșeurile cu 90% prin eliminarea ambalajelor de unică folosință. Utilizarea materialelor reutilizabile și compostarea.",
                        "Advocacy local: Petiții pentru spații verzi au rata de succes de 60% la nivel local. Participarea la ședințele consiliului local amplifică vocea cetățenilor de 10 ori."},

                {"💧", "Protecția Apei - Conservare și Purificare Naturală",
                        "Sisteme filtrare naturală: Zonele umede artificiale elimină 90% din nutrienții și 99% din bacteriile nocive. Costă 50% mai puțin decât sistemele convenționale de tratare.",
                        "Reducere pesticide: Agricultura ecologică reduce poluarea apei cu pesticide cu 97%. Metodele biologice de control (insecte benefice, rotația culturilor) sunt la fel de eficiente.",
                        "Curățare cursuri apă: O echipă de 20 voluntari poate curăța 2km de râu în 6 ore. Îndepărtarea deșeurilor îmbunătățește calitatea apei pentru ecosistemele acvatice în aval.",
                        "Monitorizare calitate: Kit-uri simple de testare detectează poluarea timpuriu. Raportarea autorităților accelerează intervenția cu 80% când comunitatea este implicată activ.",
                        "Tehnologii verzi: Biofiltele cu plante locale (papură, stuf) procesează 500L apă/m²/zi. Sistemele de lagunare costă 70% mai puțin decât stațiile de epurare clasice.",
                        "Conservare domestică: Sisteme de reciclare apă gri (de la duș, chiuvetă) pot satisface 40% din necesarul de irigație. Economii de 150m³ apă anual per gospodărie."},

                {"🌾", "Agricultura Durabilă - Hranirea Populației fără Compromisuri",
                        "Susținere fermieri eco: Fermierii biologici certificați primesc prețuri cu 20-40% mai mari. Susținerea lor prin cumpărături directe elimină intermediarii și transportul excesiv.",
                        "Produse certificate organic: Alimentele bio conțin cu 60% mai mulți antioxidanți și zero reziduuri de pesticide. Reducerea riscului de cancer cu 25% conform studiilor pe termen lung.",
                        "Grădini personale naturale: O grădină de 50m² poate produce 80kg legume anual fără pesticide. Compostul casei poate înlocui 100% fertilizatorii chimici pentru grădini mici.",
                        "Plante companion: Menta respinge furnicile, baziliconul protejează roșiile de dăunători. Aceste asocieri reduc necesarul de pesticide cu 70% în grădinile naturale.",
                        "Biodiversitate locală: Semințele autohtone sunt adaptate climatului local și necesită 50% mai puțină apă. Păstrarea varietăților tradiționale protejează patrimoniul genetic.",
                        "Tehnici regenerative: Rotația culturilor îmbunătățește fertilitatea solului cu 30% anual. Cover crops (plante de acoperire) previn eroziunea și fixează azotul atmosferic natural."}
        };
    }
}