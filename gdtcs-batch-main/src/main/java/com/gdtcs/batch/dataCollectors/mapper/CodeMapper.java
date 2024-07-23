package com.gdtcs.batch.dataCollectors.mapper;

import com.gdtcs.batch.dataCollectors.vo.CodeVo;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CodeMapper {
	List<CodeVo> selectCodeList();
}
