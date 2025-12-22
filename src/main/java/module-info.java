module org.example.combatereapoluariiapp {
    requires org.json;
    requires java.desktop;
    requires java.net.http;

    exports org.example.combatereapoluariiapp.src;
    exports org.example.combatereapoluariiapp.src.ui;
    exports org.example.combatereapoluariiapp.src.service;
    exports org.example.combatereapoluariiapp.src.model;
    exports org.example.combatereapoluariiapp.src.data;
    exports org.example.combatereapoluariiapp.src.constants;
    exports org.example.combatereapoluariiapp.src.ui.panels;
}