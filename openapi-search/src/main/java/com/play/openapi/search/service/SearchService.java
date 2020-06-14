package com.play.openapi.search.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SearchService {
    public void createIndex() throws IOException;

    public boolean existIndex() throws IOException;

    public boolean deleteIndex() throws IOException;

    public void saveData( String json ) throws IOException;

    public boolean deleteData( String id ) throws IOException;

    public List <Map> search( String json ) throws IOException;

    public Long count( String params ) throws IOException;

    public Map statAvg( long startTime, long endTime ) throws IOException;


}
