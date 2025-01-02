package eu.happycoders.shop.adapter.in.rest.common;

/**
 * An error entity with a status and message returned via REST API in case of an error.
 *
 * @author Sven Woltmann
 */
public record ErrorEntity(int httpStatus, String errorMessage) {}
