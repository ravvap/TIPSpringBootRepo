package gov.fdic.tip.controller.base;

import gov.fdic.tip.service.base.BaseService;

public abstract class AbstractBaseController<T, ID, CreateDTO, UpdateDTO, ResponseDTO> 
        implements BaseController<T, ID, CreateDTO, UpdateDTO, ResponseDTO> {

    protected final BaseService<T, ID, CreateDTO, UpdateDTO, ResponseDTO> service;
    
    protected AbstractBaseController(BaseService<T, ID, CreateDTO, UpdateDTO, ResponseDTO> service) {
        this.service = service;
    }

     
}