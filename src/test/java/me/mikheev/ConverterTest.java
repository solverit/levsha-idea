package me.mikheev;

import ch.digitalfondue.jfiveparse.Converter;
import org.junit.Test;

import javax.xml.stream.XMLStreamException;

import static org.junit.Assert.assertEquals;

/**
 * @author Andrey_Mikheev
 */
public class ConverterTest {

  @Test
  public void convertSimple() throws XMLStreamException {
    String html = "<div class=\"example\">Hello world</div>";

    String dsl = "'div(\n" +
        "  'class /= \"example\",\n" +
        "  \"Hello world\"\n" +
        ")";

    String result = Converter.convert(html);

    assertEquals(result, dsl);
  }

  @Test
  public void convertFull() throws XMLStreamException {
    String html = "<form>\n" +
        "<div class=\"field\">\n" +
        "    <div class=\"control\">\n" +
        "        <input class=\"input is-large\" type=\"email\" placeholder=\"Your Email\" autofocus=\"\">\n" +
        "    </div>\n" +
        "</div>\n" +
        "\n" +
        "<div class=\"field\">\n" +
        "    <div class=\"control\">\n" +
        "        <input class=\"input is-large\" type=\"password\" placeholder=\"Your Password\"/>\n" +
        "    </div>\n" +
        "</div>\n" +
        "<div class=\"field\">\n" +
        "    <label class=\"checkbox\">\n" +
        "        <input type=\"checkbox\"/>\n" +
        "        Remember me\n" +
        "    </label>\n" +
        "</div>\n" +
        "<button class=\"button is-block is-info is-large is-fullwidth\">Login</button>\n" +
        "</form>";

    String dsl = "'form(\n" +
        "  'div(\n" +
        "    'class /= \"field\",\n" +
        "    'div(\n" +
        "      'class /= \"control\",\n" +
        "      'input(\n" +
        "        'class /= \"input is-large\",\n" +
        "        'type /= \"email\",\n" +
        "        'placeholder /= \"Your Email\",\n" +
        "        'autofocus /= \"\"\n" +
        "      )\n" +
        "    )\n" +
        "  ),\n" +
        "  'div(\n" +
        "    'class /= \"field\",\n" +
        "    'div(\n" +
        "      'class /= \"control\",\n" +
        "      'input(\n" +
        "        'class /= \"input is-large\",\n" +
        "        'type /= \"password\",\n" +
        "        'placeholder /= \"Your Password\"\n" +
        "      )\n" +
        "    )\n" +
        "  ),\n" +
        "  'div(\n" +
        "    'class /= \"field\",\n" +
        "    'label(\n" +
        "      'class /= \"checkbox\",\n" +
        "      'input(\n" +
        "        'type /= \"checkbox\"\n" +
        "      ),\n" +
        "      \"Remember me\"\n" +
        "    )\n" +
        "  ),\n" +
        "  'button(\n" +
        "    'class /= \"button is-block is-info is-large is-fullwidth\",\n" +
        "    \"Login\"\n" +
        "  )\n" +
        ")";

    String result = Converter.convert(html);

    assertEquals(result, dsl);
  }

}