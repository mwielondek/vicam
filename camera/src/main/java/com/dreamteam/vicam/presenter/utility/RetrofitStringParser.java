package com.dreamteam.vicam.presenter.utility;

import com.dreamteam.vicam.model.errors.NotImplementedException;

import java.io.IOException;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by fsommar on 2014-04-22.
 */
public class RetrofitStringParser implements retrofit.converter.Converter {

  @Override
  public Object fromBody(TypedInput body, Type type) throws ConversionException {
    try {
      return Utils.streamToString(body.in());
    } catch (IOException e) {
      return "";
    }
  }

  @Override
  public TypedOutput toBody(Object object) {
    throw new NotImplementedException();
  }
}
