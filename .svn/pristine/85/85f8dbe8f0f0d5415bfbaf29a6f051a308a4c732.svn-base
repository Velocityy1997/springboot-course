package io.ken.springboot.course.service.implement;

import io.ken.springboot.course.bean.Training;
import io.ken.springboot.course.dao.TrainMapper;
import io.ken.springboot.course.service.ITrainService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author GouYudong
 * @create 2019-11-20 16:47
 **/

@Service
public class TrainServiceImpl implements ITrainService {

    @Resource
    private TrainMapper trainMapper;

    @Override
    public List<Training> selectByUserId(String userId) {
        return trainMapper.selectByUserId(userId);
    }
    
    @Override
    public int add(Training training) {
        return trainMapper.add(training);
    }
    
}
