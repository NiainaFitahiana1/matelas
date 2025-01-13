package com.example.matelas.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.service.annotation.GetExchange;

import com.example.matelas.entity.Bloc;
import com.example.matelas.entity.FormeUsuelle;
import com.example.matelas.entity.Transformation;
import com.example.matelas.model.MachineTrier;
import com.example.matelas.repository.BlocRepository;
import com.example.matelas.repository.DetailTransformationRepository;
import com.example.matelas.entity.DetailTransformation;
import com.example.matelas.service.BlocService;
import com.example.matelas.service.FormeUsuelleService;
import com.example.matelas.service.MachineService;
import com.example.matelas.service.TransformationService;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BlocController {
    @Autowired
    private BlocService blocService;

    @Autowired
    private FormeUsuelleService formeUsuelleService;

    @Autowired
    private TransformationService transformationService;

    @Autowired
    private MachineService machineService;

    @GetMapping("/ajoutbloc")

    public String ajoutBloc(Model model ) {
        model.addAttribute("Bloc", new Bloc());
        return "ajoutBloc"
    }
    
    @PostMapping("/ajouterbloc")
    public String addBloc(@ModelAttribute("bloc") Bloc bloc, BindingResult result, Model model ){
        if (result.hasErrors()){
            return "ajoujtBloc";
        }
        blocService.saveBloc(bloc);
        model.addAttribute("message", "Bloc ajouté avec succès!");
        return "ajoutBloc";
    }

    @GetMapping("/transformer-bloc")
    public String transformeBlocForm(Model model) {
        model.addAttribute("blocs", blocService.getAllBlocDisponible());
        model.addAttribute("forme_usuels", formeUsuelleService.getAllFormeUsuelle());
        return "transformerBloc";
    }

    @PostMapping("transformerBloc")
    public String transformeBloc(@RequestParam("blocId") Bloc bloc,
            @RequestParam("forme_usuel_id") FormeUsuelle[] forme_usuel,
            @RequestParam("quantite") int[] quantite, @RequestParam("longueur") double longueur,
            @RequestParam("largeur") double largeur, @RequestParam("hauteur") double hauteur,
            @RequestParam("date") LocalDateTime date, Model model) {

        Transformation transfomation = new Transformation();
        transfomation.setDateTransformation(date);
        transfomation.setBloc(bloc);

        DetailTransformation[] detailTransformations = new DetailTransformation[forme_usuel.length];
        for (int i = 0; i < forme_usuel.length; i++) {
            DetailTransformation detail = new DetailTransformation();
            detail.setFormeUsuelle(forme_usuel[i]);
            detail.setQuantite(quantite[i]);
            detailTransformations[i] = detail;
        }

        List<DetailTransformation> details = Arrays.asList(detailTransformations);
        double ecart = transformationService.calculateTetaEcart(bloc, details,
                longueur, largeur, hauteur);
        if (ecart > 2 || ecart < 0) {
            model.addAttribute("erreur", "L ecart est de " + ecart + " % . Verifier votre saisie de donne");
        } else {
            transformationService.saveTransformation(transfomation,
                    details, largeur, longueur, hauteur);
            model.addAttribute("success", "Succes . Ecart :" + ecart);

        }

        return "transformerBloc";
    }

    @GetMapping("/updateBloc")
    public String updateBloc(Model model) {
        model.addAttribute("blocs", blocService.getAll());

        return "update";
    }

    @PostMapping("/saveUpdate")
    @ResponseBody
    public String saveUpdate(@RequestParam("blocId") Bloc bloc, @RequestParam("prixRevient") double prixDeRevient) {
        bloc.setPrixRevient(prixDeRevient);
        blocService.updatePrixRevientAllBloc(bloc);
        return "Success";
    }

    @GetMapping("/machine")
    @ResponseBody
    public List<MachineTrier> getMachineTrier() {
        return machineService.trierMachine();
    }

    @GetMapping("/generate")
    @ResponseBody

    public String generate() {

        List<Bloc> blocs = new ArrayList<>(1000000);

        for (int i = 0; i < 1000000; i++) {
            Bloc bloc = new Bloc(true);
            blocs.add(bloc);

            if (blocs.size() >= 500000) {
                blocService.saveAllBloc(blocs);
                blocs.clear();
            }
        }

        if (!blocs.isEmpty()) {
            blocService.saveAllBloc(blocs);
        }

        return "Success";

    }

}
