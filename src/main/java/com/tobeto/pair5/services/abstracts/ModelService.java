package com.tobeto.pair5.services.abstracts;

import com.tobeto.pair5.services.dtos.model.requests.AddModelRequest;
import com.tobeto.pair5.services.dtos.model.requests.DeleteModelRequest;
import com.tobeto.pair5.services.dtos.model.requests.UpdateModelRequest;

public interface ModelService{
    void add(AddModelRequest request);
    void delete(DeleteModelRequest request);
    void update(UpdateModelRequest request);
}
