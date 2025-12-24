# EcoApp România

A comprehensive desktop application for environmental awareness and pollution reduction in Romania, built with Java Swing and powered by AI.

[![Java](https://img.shields.io/badge/Java-11-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.x-blue.svg)](https://maven.apache.org/)

> **Note**: This project was developed with AI assistance as a learning and development tool, alongside traditional programming methods.

---

## About

EcoApp România is an educational platform that combines scientific research, AI technology, and interactive visualizations to help Romanian citizens understand and combat environmental pollution. The application provides practical, data-driven solutions for reducing environmental impact.

---

## Key Features

### AI-Powered Tools
- **Smart Q&A Assistant** - Get instant answers about environmental issues using Google Gemini API
- **AI Article Summarization** - Automatically summarize scientific research papers
- **Intelligent Recommendations** - Personalized suggestions based on user data

### Environmental Analytics
- **Carbon Footprint Calculator** - Calculate and track your CO2 emissions
- **Real-time Pollution Map** - View air quality data for 10 Romanian cities
- **Pollution Trends Analyzer** - Visualize historical pollution data with interactive charts

### Educational Content
- **Scientific Article Database** - Curated research from Semantic Scholar, CrossRef, and CORE APIs
- **Environmental Quiz** - Test your knowledge with 3 difficulty levels
- **Daily Eco-Tips** - 25+ practical tips for sustainable living
- **50+ Solutions Hub** - Organized by category (Home, Transport, Office, Community)

### User Experience
- Modern UI with **Dark/Light themes**
- Smooth animations and transitions
- Responsive design
- Romanian language interface

---

## Tech Stack

- **Language:** Java 11
- **UI Framework:** Swing with custom graphics
- **Build Tool:** Maven
- **AI Integration:** Google Gemini 1.5 Flash API
- **External APIs:** Semantic Scholar, CrossRef, CORE
- **Design Patterns:** MVC, Service Layer, Repository, Observer, Factory

---

## Quick Start

### Prerequisites
- Java JDK 11+
- Maven 3.x
- Google Gemini API Key ([Get one here](https://makersuite.google.com/app/apikey))

### Installation
```bash
# Clone the repository
git clone https://github.com/yourusername/EcoApp-Romania.git
cd EcoApp-Romania

# Add your API key
# Edit src/main/java/org/example/combatereapoluariiapp/src/service/AIService.java
# Replace: private static final String API_KEY = "";
# With: private static final String API_KEY = "YOUR_KEY_HERE";

# Build and run
mvn clean package
java -jar target/combatereapoluariiapp-1.0.0.jar
```

---

## Screenshots

---

## Project Highlights

- **Full-stack development** with modern Java practices
- **API integration** from multiple scientific databases
- **AI/ML implementation** using Google Gemini
- **Complex UI/UX** with custom Swing components
- **Data visualization** with custom chart implementations
- **Asynchronous programming** with CompletableFuture
- **Clean architecture** following SOLID principles

---

## Acknowledgments

- Scientific data from Semantic Scholar, CrossRef, and CORE
- AI capabilities powered by Google Gemini
- Romanian environmental data and policies
- Developed with AI assistance for enhanced productivity

---

**If you find this project interesting, please consider giving it a star!**
