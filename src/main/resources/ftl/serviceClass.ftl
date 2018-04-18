package ${servicePackage};

import com.zyk.task.common.service.BaseService;
import ${entityPackage}.${className}Entity;
<#if columnKeyParam == 'BigInteger id'>
import java.math.BigInteger;
</#if>

/**
 * 描述：</b>${codeName}<br>
 * @author：系统生成
 * @version:1.0
 */
public interface ${className}Service extends BaseService {
	<#--
	/**
     * 描述: 插入${codeName}
     * <p>创建人：系统自动生成 , ${nowDate}</p>
     * @param ${lowerName}Entity 需要插入的实体信息
     * @return
     */
	public Integer insert(${className}Entity ${lowerName}Entity);
	
	/**
     * 描述: 更新${codeName}
     * <p>创建人：系统自动生成 , ${nowDate}</p>
     * @param ${lowerName}Entity 需要更新的实体信息
     * @return
     */
	public Integer update(${className}Entity ${lowerName}Entity);
	
	/**
     * 描述: 根据实体查询${codeName}
     * <p>创建人：系统自动生成 , ${nowDate}</p>
	 * @param ${lowerName}Entity
     * @return
     */
	public ${className}Entity get(${className}Entity ${lowerName}Entity);
	
	<#if columnKeyParam !="">
	/**
     * 描述: 根据主键删除${codeName}
     * <p>创建人：系统自动生成 , ${nowDate}</p>
     	<#list columnKeyDatas as item>
	 * @param ${item.domainPropertyName} ${item.columnComment} 
		</#list>
     * @return
     */
	public Integer delete(${columnKeyParam});
	</#if>
	
	/**
     * 描述: 根据条件查询${codeName}
     * <p>创建人：系统自动生成 , ${nowDate}</p>
     * @param ${lowerName}Entity 查询条件对应的实体信息
     * @return
     */
	public List<${className}Entity> getList(${className}Entity ${lowerName}Entity);
	
	/**
	 * 描述: 分页查询${codeName}
	 * <p>创建人：系统自动生成 , ${nowDate}</p>
	 * @param ${lowerName}Entity 分页查询对应实体信息
	 * @return
	 */
	public Pagination<${className}Entity> getPagenationList(${className}Entity ${lowerName}Entity);
	-->
	
	<#if columnKeyParam !="">
	/**
     * 描述: 根据主键查询${codeName}
     	<#list columnKeyDatas as item>
	 * @param ${item.domainPropertyName} ${item.columnComment} 
		</#list>
     * @return
     */
	public ${className}Entity get(${columnKeyParam});
	</#if>
	
	<#if getBestMatchedFlag=='Y'>
	/**
	 * 描述: 最优化查询${codeName}
		<#list columnKeyDatas as item>
	 * @param ${item.domainPropertyName} ${item.columnComment} 
		</#list>
	 * @return
	 */
	public ${className}Entity getBestMatched(${columnKeyParam});
	</#if>
	
	/*user customize code start*/
${userCustomCode}
	/*user customize code end*/
}
