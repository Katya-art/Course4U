package org.example.finalProjectSpring.service;

import org.example.finalProjectSpring.dao.MarkDao;
import org.example.finalProjectSpring.model.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link MarkService} interface.
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

@Service
public class MarkServiceImpl implements MarkService {

    @Autowired
    MarkDao markDao;

    @Override
    public Mark findMarkById(Long id) {
        return markDao.findMarkById(id);
    }
}
