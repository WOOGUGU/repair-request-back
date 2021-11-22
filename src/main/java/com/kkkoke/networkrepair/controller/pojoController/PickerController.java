package com.kkkoke.networkrepair.controller.pojoController;

import com.kkkoke.networkrepair.service.Impl.PickerTimeServiceImpl;
import com.kkkoke.networkrepair.service.PickerLocationService;
import com.kkkoke.networkrepair.service.PickerTimeService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PickerController {
    private final PickerLocationService pickerLocationService;
    private final PickerTimeService pickerTimeService;

    public PickerController(PickerLocationService pickerLocationService, PickerTimeService pickerTimeService) {
        this.pickerLocationService = pickerLocationService;
        this.pickerTimeService = pickerTimeService;
    }


}
