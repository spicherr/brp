package org.spicher.brp.views.about;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.spicher.brp.views.MainLayout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class AboutView extends VerticalLayout {

    public AboutView() {
        setSpacing(false);

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);
       // sqlCheck();

        add(new H1("spicher.org"));
        add(new H2("Wer sind wir"));
        add(new Paragraph("Text coming soon"));
        add(new H2("Was machen wir"));
        add(new Paragraph("Text coming soon"));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private void sqlCheck(){
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://s067.cyon.net:3306/spicheri_brp",
                    "spicheri_brp", "9EynsC+b(}<B!4");

            if(!con.isClosed())
                System.out.println("Successfully connected to " +
                        "MySQL server using TCP/IP...");

        } catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
        } finally {
            try {
                if(con != null)
                    con.close();
            } catch(SQLException e) {}

        }

    }
}
