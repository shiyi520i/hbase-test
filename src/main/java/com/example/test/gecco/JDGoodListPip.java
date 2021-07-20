package com.example.test.gecco;


import com.example.test.server.OptData;
import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;


@PipelineName("jdgoodlistpip")
public class JDGoodListPip  implements Pipeline<JDGoodList> {


    @Override
    public void process(JDGoodList jdGoodList) {
        new OptData().tPutData(jdGoodList);
    }
}



