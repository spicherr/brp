package org.spicher.brp;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.spicher.brp.data.entity.Persona;
import org.spicher.brp.data.entity.Role;
import org.spicher.brp.data.entity.Team;
import org.spicher.brp.data.service.PersonaService;
import org.spicher.brp.data.service.RoleService;
import org.spicher.brp.data.service.TeamService;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.swing.*;
import java.util.List;

public class FormElements {

    public static TextField getTextField(String label) {
        TextField tField = new TextField();
        tField.setLabel(label);
        tField.setRequiredIndicatorVisible(true);
        return tField;
    }

    public static IntegerField getIntegerField(String label) {
        IntegerField iField = new IntegerField();
        iField.setLabel(label);
        iField.setRequiredIndicatorVisible(true);
        return iField;
    }

    public static RadioButtonGroup getRadioPrioField(){
        RadioButtonGroup<String> radio = new RadioButtonGroup<>("Priorität");
        radio.setHelperText("Select the PRIO");
        radio.setItems("A","B","C");
        return radio;
    }
    public static RadioButtonGroup getRadioBValField(){
        RadioButtonGroup<String> radio = new RadioButtonGroup<>("Business Value");
        radio.setHelperText("Select the Business Value");
        radio.setItems("XL","L","M","S", "XS");
        return radio;
    }
    public static RadioButtonGroup<Role> getRoleRadioButtonGroup(JdbcTemplate jdbc) {
        RadioButtonGroup<Role> radioRole = new RadioButtonGroup<>("Rollenzuteilung");
        radioRole.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioRole.setHelperText("Select the Role");
        List<Role> roleList = RoleService.getAllActive(jdbc);
        radioRole.setItems(roleList);
        radioRole.setValue(roleList.get(0));
        radioRole.setRenderer(
                new ComponentRenderer<>(role -> {
                    Text textRole = new Text(String.valueOf(role.getId()) + " " + role.getName());
                    return new Div(textRole);
                }
                )
        );
        return radioRole;
    }
    public static RadioButtonGroup<Team> getTeamRadioButtonGroup(JdbcTemplate jdbc) {
        RadioButtonGroup<Team> radioTeam = new RadioButtonGroup<>("Teamzuteilung");
        radioTeam.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioTeam.setHelperText("Select the Team");
        List<Team> teamList = TeamService.getAll(jdbc);
        radioTeam.setItems(teamList);
        radioTeam.setValue(teamList.get(0));
        radioTeam.setRenderer(
                new ComponentRenderer<>(team -> {
                    Text textTeam = new Text(String.valueOf(team.getId()) + " " + team.getName());
                    return new Div(textTeam);
                }
                )
        );
        return radioTeam;
    }
    public static RadioButtonGroup<Persona> getListOfPl(JdbcTemplate jdbc) {
        RadioButtonGroup<Persona> radio = new RadioButtonGroup<>("Projektleiter");
        radio.setHelperText("Select the Leader");
        List<Persona> plList = PersonaService.getListOfPl(jdbc);
        radio.setItems(plList);
        //radio.setValue(plList.get(0));
        radio.setRenderer(
                new ComponentRenderer<>(pl -> {
                    Text txt = new Text(String.valueOf(pl.getId()) + " " + pl.getFirstName());
                    return new Div(txt);
                }
                )
        );
        return radio;
    }
}
