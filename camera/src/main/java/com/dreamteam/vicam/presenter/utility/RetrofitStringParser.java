package com.dreamteam.vicam.presenter.utility;

import com.dreamteam.vicam.model.errors.NotImplementedException;

import java.io.IOException;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * A simple http response parser for the Retrofit library.
 *
 * It is only used to parse string results from a http response and doesn't bother with having any
 * implementation for sending a String response back.
 *
 * @author Fredrik Sommar
 * @since 2014-04-22
 */
public class RetrofitStringParser implements retrofit.converter.Converter {

  /**
   * Converts the http response body to a {@linkplain java.lang.String}. If there's an error an
   * empty {@linkplain java.lang.String} is returned. {@inheritDoc}
   */
  @Override
  public Object fromBody(TypedInput body, Type type) throws ConversionException {
    try {
      return Utils.streamToString(body.in());
    } catch (IOException e) {
      return "";
    }
  }

  /**
   * NOT IMPLEMENTED!
   */
  @Override
  public TypedOutput toBody(Object object) {
    throw new NotImplementedException();
  }
}
