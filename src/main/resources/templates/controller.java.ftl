package ${package.Controller};


import org.springframework.web.bind.annotation.RequestMapping;

<#if restControllerStyle>
    import org.springframework.web.bind.annotation.RestController;
<#else>
    import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
    import ${superControllerClassPackage};
</#if>

/**
* <p>
    * ${table.comment!} 前端控制器
    * </p>
*
* @author ${author}
* @since ${date}
*/
<#if restControllerStyle>
    @RestController
<#else>
    @Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
    class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
        public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
        public class ${table.controllerName} {
        @Resource
        private I${entity}Service ${entity?uncap_first}Service;

        /**
        * 新增
        *
        * @param ${entity?uncap_first} {@link ${entity}}
        * @return {@link R}
        */
        @PostMapping("create")
        public R create(@Valid @RequestBody ${entity} ${entity?uncap_first}, BindingResult bindingResult) {
        // 表单验证
        if (bindingResult.hasErrors()) {
        return R.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        // 业务逻辑
        boolean created = ${entity?uncap_first}Service.create(${entity?uncap_first});
        if (created) {
        return R.success("创建成功");
        }

        return null;
        }

        /**
        * 删除
        *
        * @param ${entity?uncap_first}Id {@code Long}
        * @return {@link R}
        */
        @DeleteMapping("remove/{${entity?uncap_first}Id}")
        public R remove(@PathVariable Long ${entity?uncap_first}Id) {
        // 业务逻辑
        boolean deleted = ${entity?uncap_first}Service.remove(${entity?uncap_first}Id);
        if (deleted) {
        return R.success("删除成功");
        }

        return null;
        }

        /**
        * 修改
        *
        * @param ${entity?uncap_first} {@link ${entity}}
        * @return {@link R}
        */
        @PutMapping("update")
        public R update(@Valid @RequestBody ${entity} ${entity?uncap_first}, BindingResult bindingResult) {
        // 表单验证
        if (bindingResult.hasErrors()) {
        return R.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        // 业务逻辑
        boolean updated = ${entity?uncap_first}Service.update(${entity?uncap_first});
        if (updated) {
        return R.success("编辑成功");
        }

        return null;
        }

        /**
        * 获取
        *
        * @param ${entity?uncap_first}Id {@code Long}
        * @return {@link R}
        */
        @GetMapping("get/{${entity?uncap_first}Id}")
        public R get(@PathVariable Long ${entity?uncap_first}Id) {
        ${entity} ${entity?uncap_first} = ${entity?uncap_first}Service.get(${entity?uncap_first}Id);
        return R.success(${entity?uncap_first});
        }

        /**
        * 分页
        *
        * @param current {@code int} 页码
        * @param size    {@code int} 笔数
        * @return {@link R}
        */
        @GetMapping("page")
        public R page(
        @RequestParam int current, @RequestParam int size) {
        ${entity} ${entity?uncap_first} = new ${entity}();
        // TODO 查询条件，需要在参数中增加 @RequestParam(required = false)
        IPage<${entity}> page = ${entity?uncap_first}Service.page(current, size, ${entity?uncap_first});
        return R.success(page);
        }
    </#if>

    }
</#if>