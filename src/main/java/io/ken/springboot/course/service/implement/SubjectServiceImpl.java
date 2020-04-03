package io.ken.springboot.course.service.implement;

import io.ken.springboot.course.bean.Subject;
import io.ken.springboot.course.dao.exam.SubjectMapper;
import io.ken.springboot.course.service.ISubjectService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author GouYudong
 * @create 2020-01-07 9:41
 **/
@Service
public class SubjectServiceImpl implements ISubjectService {

    @Resource
    private SubjectMapper subjectMapper;

    @Override
    public List<String> getAllname() {
        return subjectMapper.queryAllname();
    }

    @Override
    public List<Subject> queryAll() {
        return subjectMapper.queryAll();
    }
}
