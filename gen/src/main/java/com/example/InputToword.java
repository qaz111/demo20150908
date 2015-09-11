package com.example;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import static org.junit.Assert.assertEquals;

public class InputToword {

    @Test
    public void modifyDocumentAndSave() throws IOException, ZipException,
            SAXException, ParserConfigurationException, TransformerException, TransformerConfigurationException {
        //读取e盘下的hello.docx文档
        ZipFile docxFile = new ZipFile(new File("D:\\金橙车管家协议.docx"));
        //解压缩后获得里面和内容相关的xml，word文档是可以解压的，大家可以解压了试试
        ZipEntry documentXML = docxFile.getEntry("word/document.xml");
        InputStream documentXMLIS = docxFile.getInputStream(documentXML);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc = dbf.newDocumentBuilder().parse(documentXMLIS);

        //获得文档里相关的节点
        Element docElement = doc.getDocumentElement();
        assertEquals("w:document", docElement.getTagName());

        Element bodyElement = (Element) docElement.getElementsByTagName("w:body").item(0);
        assertEquals("w:body", bodyElement.getTagName());

        Element pElement = (Element) bodyElement.getElementsByTagName("w:p").item(0);
        assertEquals("w:p", pElement.getTagName());

        Element rElement = (Element) pElement.getElementsByTagName("w:r").item(0);
        assertEquals("w:r", rElement.getTagName());

        Element tElement = (Element) rElement.getElementsByTagName("w:t").item(0);
        assertEquals("w:t", tElement.getTagName());

        //查找文档中的Hello, from Office 2007!文字部分
//        assertEquals("Hello, from Office 2007!",tElement.getTextContent());
        //写入新的内容
//        tElement.setTextContent("哈哈,终于可以用java写word了，Hello, Office 2007, from Java6!");
        String[] names = {"w:body", "w:p", "w:r", "w:t"};
        int n = 3;
        NodeList elementsByTagName = bodyElement.getElementsByTagName(names[n]);
        for (int i = 0; i < elementsByTagName.getLength(); i++) {
            Element element = (Element) elementsByTagName.item(i);
            System.out.print(element.getTextContent());
            if (!"w:rs".equals(element.getTagName())) {
                System.out.println();
            }
        }
/*
        Transformer t =TransformerFactory.newInstance().newTransformer();
        ByteArrayOutputStream baos =new ByteArrayOutputStream();
        t.transform(new DOMSource(doc),
                new StreamResult(baos));

        //创建新的要输出的word文档，按钮原来word文档的内容写入新的文档中。
        ZipOutputStream docxOutFile = new ZipOutputStream(new FileOutputStream("e:\\response.docx"));
        Enumeration entriesIter =docxFile.entries();
        while (entriesIter.hasMoreElements())
        {
            ZipEntry entry = (ZipEntry) entriesIter.nextElement();

            if (entry.getName().equals("word/document.xml"))
            {
                byte[] data = baos.toByteArray();
                docxOutFile.putNextEntry(new ZipEntry(entry.getName()));
                docxOutFile.write(data, 0, data.length);
                docxOutFile.closeEntry();
            }
            else
            {
                InputStream incoming =docxFile.getInputStream(entry);
                byte[] data = new byte[1024 * 16];
                int readCount =incoming.read(data, 0, data.length);
                docxOutFile.putNextEntry(new ZipEntry(entry.getName()));
                docxOutFile.write(data, 0, readCount);
                docxOutFile.closeEntry();
            }
        }
        docxOutFile.close();*/

    }

}
