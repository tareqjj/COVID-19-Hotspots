package com.springsecurity.auth.controllers;


import com.springsecurity.auth.models.Location;
import com.springsecurity.auth.models.Test;
import com.springsecurity.auth.repositories.TestRepository;
import com.springsecurity.auth.services.MapsService;
import com.springsecurity.auth.services.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Controller
public class MapsController {
    private final MapsService mapsService;
    private final TestService testService;

    public MapsController(MapsService mapsService, TestService testService) {
        this.mapsService = mapsService;
        this.testService = testService;
    }

    @RequestMapping("heatMap")
    public String heatMap(Model model){
        List<Test> tests = testService.activePoints();
//        ArrayList<Double[]> arr = new ArrayList<>();
//        for (Location location: locations) {
//            Double[] locat = new Double[2];
//            locat[0] = location.getLang();
//            locat[1] = location.getLat();
//            arr.add(locat);
//        }
//        System.out.println(arr);
        model.addAttribute("tests", tests);
        System.out.println(tests);
        return "Maps.jsp";
    }

    @RequestMapping("/inputMap")
    public String inputMap(){
        return "InputMap.jsp";
    }
    @RequestMapping("/addTest")
    public String add(@RequestParam("locations[]") List<Double[]> locations, @RequestParam("id") Long id){
        Test test = new Test("Submitted");
        testService.creatTest(test);
        for (Double[] location : locations) {
            Location newLocation = new Location(location[0], location[1]);
            mapsService.createLocations(newLocation, id, test);
        }
        return "redirect:/inputMap";
    }
}
