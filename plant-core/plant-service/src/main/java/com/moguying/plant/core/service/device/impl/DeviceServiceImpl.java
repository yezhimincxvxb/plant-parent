package com.moguying.plant.core.service.device.impl;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.annotation.Pagination;
import com.moguying.plant.core.dao.device.DeviceGatewayDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.device.DeviceGateway;
import com.moguying.plant.core.entity.device.vo.DeviceGatewayData;
import com.moguying.plant.core.service.device.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class DeviceServiceImpl implements DeviceService {


    @Value("${device.data.url}")
    private String url;

    @Value("${device.data.user}")
    private String userName;

    @Value("${device.data.password}")
    private String password;

    @Autowired
    private DeviceGatewayDAO deviceGatewayDAO;


    @Override
    @DataSource
    public ResultData<Integer> curAllData() {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,null);
        try {
            String xml = sendService("GatewayList");
            NodeList nodelist = pareXml(xml);
            if(!Objects.isNull(nodelist) && nodelist.getLength() > 0){
                for (int i = 0; i < nodelist.getLength(); i++) {
                    Node node = nodelist.item(i);
                    if(node.getNodeType()==Node.ELEMENT_NODE){
                        Element elementNode = (Element)node;
                        DeviceGateway gateway = deviceGatewayDAO.selectByGatewayLogo(elementNode.getTextContent());
                        if(Objects.isNull(gateway)){
                            DeviceGateway insert = new DeviceGateway(elementNode.getTextContent());
                            deviceGatewayDAO.insert(insert);
                        }
                    }
                }
            }

            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return resultData;
    }

    @Override
    public ResultData<List<DeviceGatewayData>> gatewayData(String gatewayLogo) {
        ResultData<List<DeviceGatewayData>> resultData = new ResultData<>(MessageEnum.ERROR,null);
        try {
            String xml = sendService("GatewayData",gatewayLogo);
            NodeList nodelist = pareXml(xml);
            List<DeviceGatewayData> deviceGatewayData = new ArrayList<>();
            if(!Objects.isNull(nodelist) && nodelist.getLength() > 0){
                for (int i = 0; i < nodelist.getLength(); i++) {
                    DeviceGatewayData data = new DeviceGatewayData();
                    Node node = nodelist.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        //field TODO 需改进
                        NodeList filedNodeList = node.getChildNodes();
                        for(int j = 0;j< filedNodeList.getLength();j++){
                            Node fieldNode = filedNodeList.item(j);
                            if (fieldNode.getNodeType() == Node.ELEMENT_NODE) {
                                NodeList valueNodeList = fieldNode.getChildNodes();
                                if(valueNodeList.item(0).getTextContent().equals("data_time")){
                                    data.setDataTime(valueNodeList.item(1).getTextContent());
                                }
                                if(valueNodeList.item(0).getTextContent().equals("gateway_logo")){
                                    data.setGatewayLogo(valueNodeList.item(1).getTextContent());
                                }
                                if(valueNodeList.item(0).getTextContent().equals("sensor_name")){
                                    data.setSensorName(valueNodeList.item(1).getTextContent());
                                }
                                if(valueNodeList.item(0).getTextContent().equals("channel_name")){
                                    data.setChannelName(valueNodeList.item(1).getTextContent());
                                }
                                if(valueNodeList.item(0).getTextContent().equals("value")){
                                    data.setValue(valueNodeList.item(1).getTextContent());
                                }
                            }
                        }
                    }
                    deviceGatewayData.add(data);
                }
            }


            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(deviceGatewayData);
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    @Override
    public String sendService(String method) throws AxisFault {
        return sendService(method,null);
    }

    @Override
    public String sendService(String method,String gateway) throws AxisFault {
        String xml =null;
        RPCServiceClient serviceClient = null ;
        serviceClient = new RPCServiceClient();
        Options options = serviceClient.getOptions();
        EndpointReference targetEPR = new EndpointReference(url);
        options.setTo(targetEPR);
        QName opAddEntry = new QName("http://action.web.iot_data_service.com",method);
        Object[] opAddEntryArgs;
        if(Objects.isNull(gateway))
            opAddEntryArgs = new Object[] {userName,password};
        else
            opAddEntryArgs = new Object[] {userName,password,gateway};
        Class[] classes = new Class[] { String.class };
        xml=(String) serviceClient.invokeBlocking(opAddEntry,opAddEntryArgs, classes)[0];
        return xml;
    }


    private NodeList pareXml(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();
        InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
        Document doc = builder.parse(inputStream);
        Element root =  doc.getDocumentElement();
        return root.getChildNodes();
    }


    @Pagination
    @Override
    @DataSource("read")
    public PageResult<DeviceGateway> deviceGatewayList(Integer page, Integer size, DeviceGateway deviceGateway) {
        deviceGatewayDAO.selectSelective(deviceGateway);
        return null;
    }

}
