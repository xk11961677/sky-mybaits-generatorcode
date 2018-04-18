package ${serviceImpPackage};

import org.springframework.stereotype.Repository;
import com.zyk.task.common.service.impl.BaseServiceImpl;
import ${servicePackage}.${className}Service;
import ${entityPackage}.${className}Entity;
<#if columnKeyParam == 'BigInteger id'>
import java.math.BigInteger;
</#if>
import lombok.extern.slf4j.Slf4j;

 /**
 * 描述：</b>${codeName}<br>
 * @author：系统生成
 * @version:1.0
 */
@Repository
@Slf4j
public class ${className}ServiceImpl extends BaseServiceImpl implements ${className}Service {

	<#--
	@Override
	public Integer insert(${className}Entity ${lowerName}Entity){
		if(${lowerName}Entity.getLastUpdateUserid() == null){
			${lowerName}Entity.setLastUpdateUserid("INTERNAL");
		}
		Date now = new Date();
		${lowerName}Entity.setCreatedDate(now);
		${lowerName}Entity.setModifiedDate(now);
		${lowerName}Entity.setLastUpdateNo(0);
		return this.superDAO.insert(${lowerName}Entity);
	}
	
	@Override
	public Integer update(${className}Entity ${lowerName}Entity){
		if(${lowerName}Entity.getLastUpdateUserid() == null){
			${lowerName}Entity.setLastUpdateUserid("INTERNAL");
		}
		Date now = new Date();
		${lowerName}Entity.setCreatedDate(null);
		${lowerName}Entity.setModifiedDate(now);
		if(${lowerName}Entity.getLastUpdateNo()==null){
			throw new IllegalArgumentException("lastUpdateNo为空！");
		}
		return this.superDAO.update(${lowerName}Entity);
	}
	
	@Override
	public ${className}Entity get(${className}Entity ${lowerName}Entity){
		return (${className}Entity)this.superDAO.get(${lowerName}Entity);
	}
	
	<#if columnKeyParam !="">
	@Override
	public Integer delete(${columnKeyParam}){
		${className}Entity ${lowerName}Entity = new ${className}Entity();
		<#list columnKeyDatas as item>
		${lowerName}Entity.set${item.domainPropertyName?cap_first}(${item.domainPropertyName});
		</#list>
		return delete(${lowerName}Entity);
	}
	</#if>
	
	@SuppressWarnings("unchecked")
	@Override
	public List<${className}Entity> getList(${className}Entity ${lowerName}Entity) {
		${lowerName}Entity.setColumnSort("${columnKeySort}");
		return (List<${className}Entity>) super.getList(${lowerName}Entity);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Pagination<${className}Entity> getPagenationList(${className}Entity ${lowerName}Entity) {
		${lowerName}Entity.setColumnSort("${columnKeySort}");
		return this.superDAO.getPagenationList(${lowerName}Entity);
	}
	-->
	
	<#if columnKeyParam !="">
	@Override
	public ${className}Entity get(${columnKeyParam}){
		${className}Entity ${lowerName}Entity = new ${className}Entity();
		<#list columnKeyDatas as item>
		${lowerName}Entity.set${item.domainPropertyName?cap_first}(${item.domainPropertyName});
		</#list>
		return (${className}Entity)get(${lowerName}Entity);
	}
	</#if>
	
	<#if columnKeyParam !="">
	<#if getBestMatchedFlag =='Y'>
	@Override
	public ${className}Entity getBestMatched(${columnKeyParam}){
		${className}Entity ${lowerName}Entity = new ${className}Entity();
		<#list columnKeyDatas as item>
		${lowerName}Entity.set${item.domainPropertyName?cap_first}(${item.domainPropertyName});
		</#list>
		return (${className}Entity)this.superDAO.getObject("${className}EntityMapper.getBestMatched",${lowerName}Entity);
	}
	</#if>
	</#if>
	
	/*user customize code start*/
${userCustomCode}
	/*user customize code end*/
}
