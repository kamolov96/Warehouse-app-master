package uz.pdp.warehouseapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.warehouseapp.dto.MeasurementDto;
import uz.pdp.warehouseapp.dto.Response;
import uz.pdp.warehouseapp.entity.Measurement;
import uz.pdp.warehouseapp.service.MeasurementService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/warehouse/measurement")
public class MeasurementController {
 final MeasurementService measurementService;

    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping
    public String showMeasurement(Model model){
        model.addAttribute("measurementDto",new MeasurementDto());
        List<Measurement> measurements=measurementService.getAllMeasurement();
        List<Measurement> collect = measurements.stream().sorted(Comparator.comparing(Measurement::getName)).collect(Collectors.toList());
        model.addAttribute("measurements",collect);

        if(measurements.isEmpty()){
            model.addAttribute("message",new Response("Not found any measurement",false));
        }else
            model.addAttribute("message",new Response("Total measurement amount: "+measurements.size(),true));
        return "/measurement/measurementOperation";
    }
    @PostMapping(path = "/add")
    public String addMeasurement(MeasurementDto measurementDto,Model model){
        Response response=measurementService.addMeasurement(measurementDto);
        model.addAttribute("measurementDto",new MeasurementDto());
        List<Measurement> measurements=measurementService.getAllMeasurement();
        List<Measurement> collect = measurements.stream().sorted(Comparator.comparing(Measurement::getName)).collect(Collectors.toList());
        model.addAttribute("measurements",collect);
        model.addAttribute("message",response);
        return "/measurement/measurementOperation";
    }
    @GetMapping(path = "/edite/{id}")
    public String editeMeasurement(@PathVariable Integer id, Model model){
        model.addAttribute("measurementDto",measurementService.getMeasurementByID(id));
        List<Measurement> measurements=measurementService.getAllMeasurement();
        List<Measurement> collect = measurements.stream().sorted(Comparator.comparing(Measurement::getName)).collect(Collectors.toList());
        model.addAttribute("measurements",collect);
        model.addAttribute("message",new Response());
        return "/measurement/editeMeasurement";
    }
    @PostMapping(path = "/edite/{id}")
    public String updateMeasurement(Measurement measurement,Model model){
        Response response=measurementService.updateMeasurement(measurement);
        if(!response.isSuccess()){
            model.addAttribute("measurementDto",measurement);
            List<Measurement> measurements=measurementService.getAllMeasurement();
            List<Measurement> collect = measurements.stream().sorted(Comparator.comparing(Measurement::getName)).collect(Collectors.toList());
            model.addAttribute("measurements",collect);
            model.addAttribute("message",response);
            return "/measurement/editeMeasurement";
        }
        model.addAttribute("measurementDto",new MeasurementDto());
        List<Measurement> measurements=measurementService.getAllMeasurement();
        List<Measurement> collect = measurements.stream().sorted(Comparator.comparing(Measurement::getName)).collect(Collectors.toList());
        model.addAttribute("measurements",collect);
        model.addAttribute("message",response);
        return "/measurement/measurementOperation";
    }
    @GetMapping(path = "/delete/{id}")
    public String deleteMeasurement(@PathVariable Integer id){
       measurementService.deleteMeasurement(id);
        return "redirect:/warehouse/measurement";
    }
}
