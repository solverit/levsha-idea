package ch.digitalfondue.jfiveparse;


import java.util.List;


/**
 * @author Andrey_Mikheev
 */
public class Converter {

  private static final String HTML = "html";
  private static final String BODY = "body";

  public static String convert(String html, String indent) {

    StringBuilder result = new StringBuilder();

    Document document = new Parser().parse(html);
    document.normalize();

    document.getChildNodes().stream()
        .filter(n -> n.getNodeName().equals(HTML))
        .flatMap(n -> n.getChildNodes().stream())
        .filter(nn -> nn.getNodeName().equals(BODY))
        .flatMap(nnn -> nnn.getChildNodes().stream())
        .forEach(node -> {
          if(result.length() != 0) {
            result.append(",\n");
          }
          result.append(nodeToSymbolDsl(node, indent));
        });

    return result.toString();
  }

  private static String nodeToSymbolDsl(Node nnode, String indent) {
    StringBuilder builder = new StringBuilder();

    if(nnode.getNodeType() == Node.ELEMENT_NODE) {
      Element node = Element.class.cast(nnode);
      builder.append("'").append(node.getNodeName().toLowerCase()).append("(\n");

      node.getAttributes().forEach(attr -> {
        builder.append(indent).append("  '").append(attr.getName()).append(" /= \"").append(normalizeText(attr.getValue())).append('"');
        builder.append(",\n");
      });

      if(!node.hasChildNodes()) {
        builder.replace(builder.length() - 2, builder.length(), "\n");
      }

      if(node.hasChildNodes()) {
        boolean perv = false;
        List<Node> childNodes = node.getChildNodes();
        for(Node childNode : childNodes) {
          String child = nodeToSymbolDsl(childNode, indent + "  ");
          if(!child.isEmpty()) {
            if(perv) {
              builder.append(",\n");
            }
            builder.append("  ").append(indent).append(child);
            perv = true;
          }
        }
        builder.append("\n");
      }

      builder.append(indent).append(")");
    }

    if(nnode.getNodeType() == Node.TEXT_NODE) {
      String text = nnode.getTextContent().trim();
      if(!text.isEmpty()) {
        if(text.indexOf('\n') > -1) {
          builder.append("\"\"\"").append(text).append("\"\"\"");
        }
        else {
          builder.append('"').append(text).append('"');
        }
      }
    }

    if(nnode.getNodeType() == Node.COMMENT_NODE) {
      builder.append("//").append(normalizeText(nnode.getTextContent()));
    }


    return builder.toString();
  }

  private static String normalizeText(String html) {
    String res = html.replace("/\n/g", " ");
    while(res.contains("  ")) {
      res = res.replace("  ", " ");
    }
    return res;
  }

}
