package com.zjut.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjut.eduservice.entity.EduSubject;
import com.zjut.eduservice.entity.excel.SubjectData;
import com.zjut.eduservice.entity.subject.oneSubject;
import com.zjut.eduservice.entity.subject.twoSubject;
import com.zjut.eduservice.listener.SubjectExcelListener;
import com.zjut.eduservice.mapper.EduSubjectMapper;
import com.zjut.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-01
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    // 添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {

        try{
            // 文件输入流
            InputStream in = file.getInputStream();
            // 读取数据
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<oneSubject> getOneTwoSubject() {
        /**
         * 1. 分别获取一级分类list 二级分类list
         * 2. 遍历一级分类list，将每一个数据的值赋给oneSubject
         * 3. 每次得到一个一级oneSubject时，遍历二级分类
         * 4. 将每一行二级分类记录赋值给一个twoSubject，将所有一级目录下的二级分类放入一个list
         * 5. 将每个二级分类的list作为一级分类的children属性中
         */
        // 1
        List<oneSubject> resultList = new ArrayList<>();
        QueryWrapper wrapperOne = new QueryWrapper();
        wrapperOne.eq("parent_id","0");
        QueryWrapper wrapperTwo = new QueryWrapper();
        wrapperTwo.ne("parent_id","0");
        // baseMapper对应的就是该service对应的dao的mapper
        List<EduSubject> oneList = baseMapper.selectList(wrapperOne);
        List<EduSubject> twoList = baseMapper.selectList(wrapperTwo);

        // 遍历每一个一级分类
        for(int i =0; i<oneList.size(); i++){
            oneSubject oneSubject = new oneSubject();
            // oneSubject.setId(oneList.get(i).getId());
            // oneSubject.setTitle(oneList.get(i).getTitle());
            // 拷贝同名属性
            BeanUtils.copyProperties(oneList.get(i),oneSubject);
            // 该一级类的二级类属性
            List<twoSubject> childrenList = new ArrayList<>();
            // 遍历二级类数组，将pid等于该一级类的id的加入到该二级类list中
            for(int j=0; j<twoList.size(); j++){
                System.out.println("+++++++++++++++++++++");
                // 比较的是两个String型变量，不能用==
                if(twoList.get(j).getParentId().equals(oneSubject.getId())){
                    System.out.println("=================");
                    twoSubject twoSubject = new twoSubject();
                    BeanUtils.copyProperties(twoList.get(j),twoSubject);


                    childrenList.add(twoSubject);
                }
            }
            // 将二级分类list加入一级的属性中
            oneSubject.setChildren(childrenList);
            // 将该一级类加入到一级类list中
            resultList.add(oneSubject);
        }

        return resultList;
    }
}
