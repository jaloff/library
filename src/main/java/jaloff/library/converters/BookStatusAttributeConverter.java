package jaloff.library.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import jaloff.library.entities.Book.Status;

@Converter(autoApply=true)
public class BookStatusAttributeConverter implements AttributeConverter<Status, String> {

	@Override
	public String convertToDatabaseColumn(Status attribute) {
		return attribute.toString();
	}

	@Override
	public Status convertToEntityAttribute(String dbData) {
		return Status.valueOf(dbData);
	}

}
