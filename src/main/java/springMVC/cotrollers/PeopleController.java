package springMVC.cotrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springMVC.dao.PeopleDAO;
import springMVC.model.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleDAO peopleDAO;

    @Autowired
    public PeopleController(PeopleDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    @GetMapping()
    public String getPeople(Model model) {
        model.addAttribute("people", peopleDAO.getPeople());
        return "peopleController/people";
    }

    @GetMapping("/{id}")
    public String getPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("people", peopleDAO.getPerson(id));
        return "peopleController/person";
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "peopleController/new";
    }

    @PostMapping()
    public String savePerson(@ModelAttribute ("person") @Valid Person person,
                             BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "peopleController/new";
        peopleDAO.savePerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleDAO.getPerson(id));
        return "peopleController/edit";
    }

    @PatchMapping ("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if(bindingResult.hasErrors())
            return "peopleController/edit";
        peopleDAO.updatePerson(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleDAO.deletePerson(id);
        return "redirect:/people";
    }

}

