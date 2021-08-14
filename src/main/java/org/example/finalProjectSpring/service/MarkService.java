package org.example.finalProjectSpring.service;

import org.example.finalProjectSpring.model.Mark;

/**
 * Service class for {@link org.example.finalProjectSpring.model.Mark}
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

public interface MarkService {

    Mark findMarkById(Long id);
}
