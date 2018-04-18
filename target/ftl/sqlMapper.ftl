<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<!-- 功能模块: ${codeName} -->
<mapper namespace="${className}EntityMapper" >

	<!--通用表字段列表-->
	<resultMap id="BaseResultMap" type="${entityPackage}.${className}Entity">
		<#list columnDatas as item>
		<result column="${item.columnName}" property="${item.domainPropertyName}" jdbcType="${item.jdbcDataType}"/>
		</#list>
    </resultMap>
    <!--通用表字段列表-->
    
	<!--user customize code start-->
${userCustomCode}
	<!--user customize code end  -->
    
	<!--通用查询条件组装-->
	<sql id="whereContation">
		<#list columnDatas as item>
			<if test="${item.domainPropertyName} != null">
				AND  ${item.columnName}=${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcDataType}}  
			</if>
		</#list>
	</sql>
	
	<!--查询字段列表拼装-->
	<sql id="baseColumnList">
		<#list columnDatas as item>
			<#if item_index==0>
			 ${item.columnName}		 
			<#else>
			,${item.columnName}		 
			</#if>
		</#list> 	
	</sql>
	
	<!--
	方法名称: insert
	调用路径: ${className}EntityMapper.insert
	开发信息: 
	处理信息: 保存记录
	-->
 	<insert id="insert" parameterType="${entityPackage}.${className}Entity" >
	 	INSERT  INTO  ${tableNameUpper}
	 		<trim prefix="(" suffix=")" suffixOverrides=",">
				<#list columnDatas as item>
					<if test="${item.domainPropertyName} != null">
					${item.columnName},
					</if>
				</#list>
			</trim>
			<trim prefix="values (" suffix=")" suffixOverrides=",">
				 <#list columnDatas as item>
					<if test="${item.domainPropertyName} != null">
					 ${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcDataType}},
					</if>
				 </#list>
			</trim>
	</insert>
	
	<!--
	方法名称: update
	调用路径: ${className}EntityMapper.update
	开发信息: 
	处理信息: 修改记录
	-->
 	<update id="update" parameterType="${entityPackage}.${className}Entity" >
		UPDATE   ${tableNameUpper}  	 
	  	<set> 
		<#list columnDatas as item>
			<#if item.columnKey !='PRI' >
				<#if item.columnName == 'LAST_UPDATE_NO'>
				<if test="${item.domainPropertyName} != null">
			 		${item.columnName} = ${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcDataType}}+1,
			 	</if>
				<#else>
				<if test="${item.domainPropertyName} != null">
			 		${item.columnName} = ${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcDataType}},
			 	</if>
			 	</#if>
			</#if>
		</#list>
		</set>
		WHERE  
		<#list columnKeyDatas as item>
			<#if item_index==0>
			${item.columnName} = ${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcDataType}}		 
			<#else>
		 	AND ${item.columnName} = ${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcDataType}}		 
			</#if>
		</#list>
		<#list columnDatas as item>
			<#if item.columnName == 'LAST_UPDATE_NO'>
		<if test="${item.domainPropertyName} != null">
	 		AND ${item.columnName} = ${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcDataType}}  
	 	</if>
		 	</#if>
		</#list>
	</update>
	
	<!--
	方法名称: updateBak
	调用路径: ${className}EntityMapper.updateBak
	开发信息: 
	处理信息: 修改记录
	-->
 	<update id="updateBak" parameterType="${entityPackage}.${className}Entity" >
		UPDATE   ${tableNameUpper}  	 
	  	<set> 
		<#list columnDatas as item>
			<#if item.columnKey !='PRI' >
				<#if item.columnName == 'LAST_UPDATE_NO'>
			 		${item.columnName} = ${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcDataType}}+1,
				<#else>
			 		${item.columnName} = ${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcDataType}},
			 	</#if>
			</#if>
		</#list>
		</set>
		WHERE  
		<#list columnKeyDatas as item>
			<#if item_index==0>
			${item.columnName} = ${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcDataType}}		 
			<#else>
		 	AND ${item.columnName} = ${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcDataType}}		 
			</#if>
		</#list>
		<#list columnDatas as item>
			<#if item.columnName == 'LAST_UPDATE_NO'>
	 		AND ${item.columnName} = ${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcDataType}}  
		 	</#if>
		</#list>
	</update>
	
	<#if columnKeyParam !="">
	<!--
	方法名称: deleteByPriKey
	调用路径:${className}EntityMapper.deleteByPriKey
	开发信息: 
	处理信息: 删除记录
	-->
	<delete id="deleteByPriKey" parameterType="${entityPackage}.${className}Entity">
		DELETE 	FROM ${tableNameUpper} 	 
		WHERE 
		<#list columnKeyDatas as item>
			<#if item_index==0>
			${item.columnName} = ${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcDataType}}		 
			<#else>
		 	AND ${item.columnName} = ${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcDataType}}		 
			</#if>
		</#list>
	</delete>
	</#if>
	
	<#if columnKeyParam !="">
	<!--
	方法名称: findByPriKey
	调用路径: ${className}EntityMapper.findByPriKey
	开发信息: 
	处理信息: 根据主键查询记录
	-->
	<select id="findByPriKey" parameterType="${entityPackage}.${className}Entity"  resultType="${entityPackage}.${className}Entity">
		SELECT   
		   <include refid="baseColumnList"/>
		FROM   ${tableNameUpper}         
		WHERE
		<#list columnKeyDatas as item>
				<#if item_index==0>
				${item.columnName} = ${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcDataType}}		 
				<#else>
			 	AND ${item.columnName} = ${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcDataType}}		 
				</#if>
		</#list>
	</select>
	</#if>
	
	<!--
	方法名称: getPagenationList
	调用路径: ${className}EntityMapper.getPagenationList
	开发信息: 
	处理信息: 分页查询记录
	-->
	<select id="getPagenationList" parameterType="${entityPackage}.${className}Entity"  resultType="${entityPackage}.${className}Entity">
		<!-- 分页条 -->
		<include refid="CommonEntity.paginationPrefix"/>
		SELECT   
	 		<include refid="baseColumnList"/>
		FROM   ${tableNameUpper}          
		WHERE 1=1
		 	<include refid="whereContation"/>
		 	<if test="columnSort != null">
		 	ORDER BY ${"$"}{columnSort}
		 	</if>
		<!-- 分页条 -->
		<include refid="CommonEntity.paginationSuffix"/>
	</select>
	
	<!--
	方法名称: getPagenationList-count
	调用路径: ${className}EntityMapper.getPagenationList-count
	开发信息: 
	处理信息: 查询记录数
	-->
	<select id="getPagenationList-count" parameterType="${entityPackage}.${className}Entity" resultType="int">
		SELECT count(1)  FROM  ${tableNameUpper}     
		 WHERE 1=1
		<include refid="whereContation"/>
	</select>
	
	<!--
	方法名称: getList
	调用路径: ${className}EntityMapper.getList
	开发信息: 
	处理信息: 根据条件查询记录
	-->
	<select id="getList" parameterType="${entityPackage}.${className}Entity"  resultType="${entityPackage}.${className}Entity">
	   SELECT   
	   		<include refid="baseColumnList"/>
	   FROM   ${tableNameUpper}           
	   WHERE 1=1
		<include refid="whereContation"/>
		<if test="columnSort != null">
		 	ORDER BY ${"$"}{columnSort}
		</if>
		limit 0,100
	</select>
	
	<#if getBestMatchedFlag=='Y'>
	<!--
	方法名称: getBestMatched
	调用路径: ${className}EntityMapper.getBestMatched
	开发信息: 
	处理信息: 根据条件查询记录
	-->
	<select id="getBestMatched" parameterType="${entityPackage}.${className}Entity"  resultType="${entityPackage}.${className}Entity">
	   SELECT   
	   		<include refid="baseColumnList"/>
	   FROM   ${tableNameUpper}           
	   WHERE ROWNUM=1
	   <#list columnKeyDatas as item>
			<#if item.columnName=='PRODUCT_ID' || item.columnName=='ACCOUNT_TYPE_ID'>
			AND (${item.columnName}='~~~~' OR ${item.columnName}=${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcDataType}})
			<#else>
			AND ${item.columnName} = ${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcDataType}}
			</#if>
		</#list>
	 	ORDER BY PRODUCT_ID,ACCOUNT_TYPE_ID
	</select>
	</#if>
	
	<!--
	方法名称: batchInsert
	调用路径: ${className}EntityMapper.batchInsert
	开发信息: 
	处理信息: 保存记录
	-->
 	<insert id="batchInsert" parameterType="java.util.List" >
	 	INSERT  INTO  ${tableNameUpper}
	 		<trim prefix="(" suffix=")" suffixOverrides=",">
				<#list columnDatas as item>
					${item.columnName},
				</#list>
			</trim>
			values
			<foreach collection="list" item="entity" separator=",">
			<trim prefix="(" suffix=")" suffixOverrides=",">
				 <#list columnDatas as item>
					 ${"#"}{entity.${item.domainPropertyName},jdbcType=${item.jdbcDataType}},
				 </#list>
			</trim>
			</foreach>
	</insert>
	<!--
	方法名称: batchUpdate
	调用路径: ${className}EntityMapper.batchUpdate
	开发信息: 
	处理信息: 修改记录
	-->
 	<update id="batchUpdate" parameterType="java.util.List" >
 		<foreach collection="list" item="entity" separator=",">
		UPDATE   ${tableNameUpper}  	 
	  	<set> 
		<#list columnDatas as item>
			<#if item.columnKey !='PRI' >
				<#if item.columnName == 'LAST_UPDATE_NO'>
				<if test="${item.domainPropertyName} != null">
			 		${item.columnName} = ${"#"}{entity.${item.domainPropertyName},jdbcType=${item.jdbcDataType}}+1,
			 	</if>
				<#else>
				<if test="${item.domainPropertyName} != null">
			 		${item.columnName} = ${"#"}{entity.${item.domainPropertyName},jdbcType=${item.jdbcDataType}},
			 	</if>
			 	</#if>
			</#if>
		</#list>
		</set>
		WHERE  
		<#list columnKeyDatas as item>
			<#if item_index==0>
			${item.columnName} = ${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcDataType}}		 
			<#else>
		 	AND ${item.columnName} = ${"#"}{entity.${item.domainPropertyName},jdbcType=${item.jdbcDataType}}		 
			</#if>
		</#list>
		<#list columnDatas as item>
			<#if item.columnName == 'LAST_UPDATE_NO'>
		<if test="${item.domainPropertyName} != null">
	 		AND ${item.columnName} = ${"#"}{entity.${item.domainPropertyName},jdbcType=${item.jdbcDataType}}  
	 	</if>
		 	</#if>
		</#list>
		</foreach>
	</update>
</mapper>