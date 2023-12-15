package com.tobeto.pair5.services.abstracts;

import com.tobeto.pair5.services.dtos.color.requests.AddColorRequest;
import com.tobeto.pair5.services.dtos.color.requests.DeleteColorRequest;
import com.tobeto.pair5.services.dtos.color.requests.UpdateColorRequest;
import com.tobeto.pair5.services.dtos.color.responses.GetAllColorResponse;

public interface ColorService {
    void add(AddColorRequest request);
    void delete(DeleteColorRequest request);
    void update(UpdateColorRequest request);
    GetAllColorResponse getById(int id);
}
