package com.github.olson1998.openai.model;

import com.github.olson1998.http.HttpHeaders;
import com.github.olson1998.http.ReadOnlyHttpHeaders;
import com.github.olson1998.http.contract.ClientHttpResponse;
import com.github.olson1998.http.contract.WebResponse;
import com.github.olson1998.http.serialization.ResponseMapping;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Optional;

@UtilityClass
public class TestUtil {

    public static <T> WebResponse<T> webResponse(int statusCode, HttpHeaders httpHeaders, T responseBody){
        return new ClientHttpResponse<>(statusCode, httpHeaders, responseBody);
    }

    public static <T> WebResponse<T> webResponseOk(T responseBody){
        return new ClientHttpResponse<>(200, new ReadOnlyHttpHeaders(), responseBody);
    }

    public boolean isResponseMappingForType(Object arg, TypeMapping typeMapping){
        if(arg instanceof ResponseMapping<?> responseMapping){
            var pojoType = responseMapping.getPojoType();
            return isEqType(typeMapping, pojoType);
        }else {
            return false;
        }
    }

    private boolean isEqType(TypeMapping typeMapping, Type type){
        if(type instanceof Class clazz){
            return isEqClass(typeMapping, clazz);
        } else if (type instanceof ParameterizedType parametrizedType) {
            return isEqParamType(typeMapping, parametrizedType);
        }else {
            throw new IllegalArgumentException("unknown type: " + type.getTypeName());
        }
    }

    private boolean isEqClass(TypeMapping typeMapping, Class<?> clazz){
        return clazz.equals(typeMapping.type);
    }

    private boolean isEqParamType(TypeMapping typeMapping, ParameterizedType parameterizedType){
        var rawType =(Class<?>) parameterizedType.getRawType();
        if(!isEqClass(typeMapping, rawType)){
            return false;
        }
        var argsMappings = typeMapping.genericMappings;
        var args = parameterizedType.getActualTypeArguments();
        if(argsMappings == null && args == null){
            return true;
        } else if (argsMappings != null && args != null){
            return isEqTypeMappings(argsMappings, args);
        }else {
            return false;
        }
    }

    private boolean isEqTypeMappings(@NonNull TypeMapping[] typeMappings, @NonNull Type[] types){
        var typesQty = types.length;
        var mappingsQty = typeMappings.length;
        if(typesQty != mappingsQty){
            return false;
        }
        boolean[] matches = new boolean[types.length];
        for(int i = 0; i < typeMappings.length; i++){
            var typeMapping = typeMappings[i];
            var type = types[i];
            matches[i] = isEqType(typeMapping, type);
        }
        boolean isParamsEq = true;
        for(boolean isEq : matches){
            if(!isEq){
                isParamsEq = false;
                break;
            }
        }
        return isParamsEq;
    }

    @Getter
    @EqualsAndHashCode
    @RequiredArgsConstructor
    public final class TypeMapping{

        private final Class<?> type;
        private final TypeMapping[] genericMappings;

        public boolean hasArguments(){
            return genericMappings != null;
        }

        @Override
        public String toString() {
            var typeString = new StringBuilder("TypeMapping[")
                    .append("type=").append(type);
            Optional.ofNullable(genericMappings).ifPresent(args ->{
                typeString.append(", genericMappings=").append(Arrays.toString(genericMappings));
            });
            typeString.append(']');
            return typeString.toString();
        }
    }

}
