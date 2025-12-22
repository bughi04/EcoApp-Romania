package org.example.combatereapoluariiapp.src.model;

public class CarbonFootprintCalculator {

    // Carbon emission factors (kg CO2 per unit)
    private static final double CAR_EMISSIONS_PER_KM = 0.21; // Average car
    private static final double FLIGHT_EMISSIONS_PER_KM = 0.255; // Domestic flight
    private static final double BUS_EMISSIONS_PER_KM = 0.089;
    private static final double TRAIN_EMISSIONS_PER_KM = 0.041;

    // Energy emissions (kg CO2 per kWh) - Romania specific
    private static final double ELECTRICITY_EMISSIONS_PER_KWH = 0.39;
    private static final double GAS_EMISSIONS_PER_KWH = 0.185;

    // Lifestyle factors
    private static final double MEAT_HEAVY_DIET_ANNUAL = 2500;
    private static final double VEGETARIAN_DIET_ANNUAL = 1500;
    private static final double VEGAN_DIET_ANNUAL = 1000;

    public static class CarbonResult {
        private double totalAnnualCO2;
        private double transportCO2;
        private double energyCO2;
        private double lifestyleCO2;
        private String recommendation;

        public CarbonResult(double totalAnnualCO2, double transportCO2, double energyCO2,
                            double lifestyleCO2, String recommendation) {
            this.totalAnnualCO2 = totalAnnualCO2;
            this.transportCO2 = transportCO2;
            this.energyCO2 = energyCO2;
            this.lifestyleCO2 = lifestyleCO2;
            this.recommendation = recommendation;
        }

        // Getters
        public double getTotalAnnualCO2() { return totalAnnualCO2; }
        public double getTransportCO2() { return transportCO2; }
        public double getEnergyCO2() { return energyCO2; }
        public double getLifestyleCO2() { return lifestyleCO2; }
        public String getRecommendation() { return recommendation; }
    }

    public static CarbonResult calculateFootprint(
            double carKmPerYear, double flightKmPerYear, double busKmPerYear, double trainKmPerYear,
            double electricityKwhPerMonth, double gasKwhPerMonth,
            String dietType) {

        // Transport emissions
        double transportCO2 = (carKmPerYear * CAR_EMISSIONS_PER_KM) +
                (flightKmPerYear * FLIGHT_EMISSIONS_PER_KM) +
                (busKmPerYear * BUS_EMISSIONS_PER_KM) +
                (trainKmPerYear * TRAIN_EMISSIONS_PER_KM);

        // Energy emissions (monthly to annual)
        double energyCO2 = (electricityKwhPerMonth * ELECTRICITY_EMISSIONS_PER_KWH * 12) +
                (gasKwhPerMonth * GAS_EMISSIONS_PER_KWH * 12);

        // Lifestyle emissions
        double lifestyleCO2;
        switch (dietType.toLowerCase()) {
            case "vegetarian":
                lifestyleCO2 = VEGETARIAN_DIET_ANNUAL;
                break;
            case "vegan":
                lifestyleCO2 = VEGAN_DIET_ANNUAL;
                break;
            default:
                lifestyleCO2 = MEAT_HEAVY_DIET_ANNUAL;
                break;
        }

        double totalCO2 = transportCO2 + energyCO2 + lifestyleCO2;

        // Generate recommendation
        String recommendation = generateRecommendation(totalCO2, transportCO2, energyCO2, lifestyleCO2);

        return new CarbonResult(totalCO2, transportCO2, energyCO2, lifestyleCO2, recommendation);
    }

    private static String generateRecommendation(double total, double transport,
                                                 double energy, double lifestyle) {
        StringBuilder rec = new StringBuilder();

        // Romanian average is about 4.5 tons CO2/year
        if (total > 4500) {
            rec.append("🚨 Amprenta ta este peste media românească (4.5 tone/an)!\n\n");
        } else {
            rec.append("✅ Amprenta ta este sub media românească!\n\n");
        }

        // Highest impact area recommendations
        if (transport > energy && transport > lifestyle) {
            rec.append("🚗 TRANSPORT (cel mai mare impact - ").append(String.format("%.1f", transport))
                    .append(" kg CO2/an):\n");
            rec.append("• Folosește transportul public sau bicicleta\n");
            rec.append("• Consideră un vehicul electric sau hibrid\n");
            rec.append("• Evită zborurile scurte când e posibil\n\n");
        }

        if (energy > transport && energy > lifestyle) {
            rec.append("⚡ ENERGIE (cel mai mare impact - ").append(String.format("%.1f", energy))
                    .append(" kg CO2/an):\n");
            rec.append("• Instalează panouri solare (Casa Verde)\n");
            rec.append("• Îmbunătățește izolația termică\n");
            rec.append("• Folosește LED-uri și aparate eficiente\n\n");
        }

        if (lifestyle > transport && lifestyle > energy) {
            rec.append("🍽️ STIL DE VIAȚĂ (cel mai mare impact - ").append(String.format("%.1f", lifestyle))
                    .append(" kg CO2/an):\n");
            rec.append("• Reduce consumul de carne roșie\n");
            rec.append("• Cumpără produse locale și de sezon\n");
            rec.append("• Evită risipa alimentară\n\n");
        }

        rec.append("🎯 Obiectiv: Sub 2 tone CO2/an pentru compatibilitate cu Acordul de la Paris");

        return rec.toString();
    }
}