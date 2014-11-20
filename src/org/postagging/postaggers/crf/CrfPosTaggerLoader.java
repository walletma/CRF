package org.postagging.postaggers.crf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.postagging.crf.CrfModel;
import org.postagging.postaggers.PosTaggerLoader;
import org.postagging.utilities.PosTaggerException;

/**
 * 
 * @author Asher Stern
 * Date: Nov 20, 2014
 *
 */
public class CrfPosTaggerLoader implements PosTaggerLoader
{
	@Override
	public CrfPosTagger load(File directory)
	{
		if (!directory.exists()) {throw new PosTaggerException("Given directory: "+directory.getAbsolutePath()+" does not exist.");}
		if (!directory.isDirectory()) {throw new PosTaggerException("The loader requires a directory, but was provided with a file: "+directory.getAbsolutePath()+".");}
		
		try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(directory, CrfPosTaggerTrainer.SAVE_LOAD_FILE_NAME ))))
		{
			@SuppressWarnings("unchecked")
			CrfModel<String, String> model = (CrfModel<String, String>) inputStream.readObject();
			return new CrfPosTagger(model);
		}
		catch (IOException | ClassNotFoundException e)
		{
			throw new PosTaggerException("Loading pos tagger failed.",e);
		}
	}

}
