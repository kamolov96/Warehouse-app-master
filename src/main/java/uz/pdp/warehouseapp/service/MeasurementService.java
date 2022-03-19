package uz.pdp.warehouseapp.service;

import org.springframework.stereotype.Service;
import uz.pdp.warehouseapp.dto.MeasurementDto;
import uz.pdp.warehouseapp.dto.Response;
import uz.pdp.warehouseapp.entity.Measurement;
import uz.pdp.warehouseapp.repository.MeasurementRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {
    final MeasurementRepository measurementRepository;

    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public List<Measurement> getAllMeasurement() {
        return measurementRepository.findAll();
    }

    public Response addMeasurement(MeasurementDto measurementDto) {
      Response response=new Response();
        boolean byName = measurementByName(measurementDto.getName());
      if(byName){
          response.setMessage("This "+measurementDto.getName()+" - measurement name already exist");
          return response;
      }
        Measurement measurement=new Measurement(measurementDto.getName(),measurementDto.isActive());
        measurementRepository.save(measurement);
        response.setMessage("Add measurement");
        response.setSuccess(true);
        return response;
    }
    public boolean measurementByName(String name){
        for (Measurement category : measurementRepository.findAll()) {
            if(category.getName().trim().equalsIgnoreCase(name.trim()))
                return true;
        }
        return false;
    }

    public Measurement getMeasurementByID(Integer id) {
        return measurementRepository.findById(id).orElse(new Measurement());
    }
    public Response updateMeasurement(Measurement measurement) {
        Response response=new Response();
        boolean hasName=false;
        for (Measurement measurement1 : measurementRepository.findAll()) {
            if (measurement1.getName().trim().equalsIgnoreCase(measurement.getName().trim()) && !measurement1.getId().equals(measurement.getId())) {
                hasName = true;
                break;
            }
        }
        if(!hasName) {
            measurementRepository.save(measurement);
            response.setSuccess(true);
            response.setMessage("Edite measurement");
            return response;
        }
        response.setMessage("This name already exist");
        return response;
    }

    public Response deleteMeasurement(Integer id) {
        Response response=new Response();
        Optional<Measurement> byId = measurementRepository.findById(id);
        if (byId.isPresent()) {
            Measurement measurement = byId.get();
            measurement.setActive(false);
            measurementRepository.save(measurement);
            response.setSuccess(true);
            response.setMessage("This measurement is no active");
            return response;
        }
        response.setMessage("Not found measurement");
        return response;
    }
}
